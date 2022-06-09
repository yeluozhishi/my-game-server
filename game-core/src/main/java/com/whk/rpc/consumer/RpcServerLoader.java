package com.whk.rpc.consumer;

import com.whk.rpc.Constats.RpcSystemConfig;
import com.whk.rpc.consumer.server.Server;
import com.whk.rpc.consumer.server.ServerManager;
import com.whk.rpc.serialize.RpcSerializeProtocol;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * 服务加载器
 */
public class RpcServerLoader {

    private final Logger logger = Logger.getLogger(RpcServerLoader.class.getName());

    private final ConcurrentHashMap<String, MessageSendHandler> serviceMap = new ConcurrentHashMap<>();
    private final ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
    private static RpcServerLoader rpcServerLoader;
    private final Lock lock = new ReentrantLock();
    private final Condition connectStatus = lock.newCondition();
    private final Condition handlerStatus = lock.newCondition();
    private ServerManager serverManager;
    // 协议类型
    private RpcSerializeProtocol serializeProtocol;

    private RpcServerLoader() {
    }

    public static RpcServerLoader getInstance() {
        if (rpcServerLoader == null) {
            synchronized (RpcServerLoader.class) {
                if (rpcServerLoader == null) {
                    rpcServerLoader = new RpcServerLoader();
                }
            }
        }
        return rpcServerLoader;
    }

    public void setInfo(RpcSerializeProtocol serializeProtocol){
        setSerializeProtocol(serializeProtocol);
        if (serverManager == null) {
            serverManager = ServerManager.getInstance();
        }
    }

    public void load(Server server, String remark) throws ExecutionException, InterruptedException {
        serverManager.addServer(server);
        if (serviceMap.containsKey(server.getAddress())) {
            return;
        }
        if (serializeProtocol == null){
            throw new NullPointerException("协议信息未配置");
        }

        final InetSocketAddress remoteAddr = new InetSocketAddress(server.ip(), server.port());
        connect(remoteAddr, serializeProtocol, remark);
    }

    // 建立连接
    public void connect(InetSocketAddress remoteAddr, RpcSerializeProtocol serializeProtocol, String remark) throws ExecutionException, InterruptedException {

        Future<Boolean> future = threadPoolExecutor.submit(
                new MessageSendInitializeTask(this, new NioEventLoopGroup(), remoteAddr, serializeProtocol, remark));

        if (future.get()){
            lock.lock();
            try {
                if (!serviceMap.containsKey(remoteAddr.toString())) {
                    handlerStatus.await();
                }

                if (serviceMap.containsKey(remoteAddr.toString())) {
                    connectStatus.signalAll();
                }
            } catch (InterruptedException ex) {
                logger.severe("rpc server start failed.. " + ex);
            } finally {
                lock.unlock();
            }
        }
    }

    public void setMessageSendHandler(InetSocketAddress remoteAddr, MessageSendHandler messageInHandler) {
        try {
            lock.lock();
            serviceMap.put(remoteAddr.toString(), messageInHandler);
            handlerStatus.signal();
        } finally {
            lock.unlock();
        }
    }

    public MessageSendHandler getMessageSendHandler(int serverId) throws ExecutionException, InterruptedException {
        var server = serverManager.getServer(serverId);
        if (server.isEmpty()) {
            logger.severe("no this server id");
            return null;
        }
        if (!serviceMap.containsKey(server.get().getAddress())){
            // 没有该句柄，进行加载
            load(server.get(),"");
            try {
                lock.lock();
                if (!serviceMap.containsKey(server.get().getAddress())) {
                    boolean timeout = !connectStatus.await(RpcSystemConfig.SYSTEM_PROPERTY_MESSAGE_CALLBACK_TIMEOUT,
                            TimeUnit.MILLISECONDS);
                    if (timeout) {
                        throw new TimeoutException(RpcSystemConfig.TIMEOUT_RESPONSE_MSG);
                    }
                }
            } catch (InterruptedException | TimeoutException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return serviceMap.get(server.get().getAddress());
    }

    public void unLoad() {
        serviceMap.values().forEach(MessageSendHandler::close);
        threadPoolExecutor.shutdown();
    }

    public ConcurrentHashMap<String, MessageSendHandler> getServiceMap() {
        return serviceMap;
    }

    public RpcSerializeProtocol getSerializeProtocol() {
        return serializeProtocol;
    }

    public void setSerializeProtocol(RpcSerializeProtocol serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }
}
