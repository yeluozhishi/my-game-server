package com.whk.net.rpc.proxy;

import com.whk.net.rpc.registry.RegistryHandler;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.consumer.DefaultRpcPromise;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import com.whk.threadpool.HandlerFactory;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.processor.ProcessorManager;
import lombok.Getter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public enum RpcProxyHolder {
    // 实例
    INSTANCE;

    private final Logger logger = Logger.getLogger(RpcProxyHolder.class.getName());

    /**
     * rpc发送服务
     */
    private GameRpcService rpcService;

    private RegistryHandler registryHandler;

    private ThreadPoolExecutor threadPoolExecutor;

    @Getter
    private String responseTopic;

    RpcProxyHolder() {
    }

    public void init(GameRpcService rpcService, String responseTopic) {
        this.rpcService = rpcService;
        this.responseTopic = responseTopic;
        registryHandler = new RegistryHandler();
        threadPoolExecutor = ThreadPoolManager.getInstance().getExecutor(ThreadType.RPC_THREAD);
        logger.warning("rpc 初始化完成！");
    }


    public IRpcService getInstance(Class<?> clazz, String topic, long orderId) {
        return (IRpcService) RpcProxy.create(clazz, topic, orderId);
    }

    public Object sendRpcMessage(MessageRequest msg, String topic) {
        try {
            // 接收方可以用topic返回消息
            msg.setResponseTopic(this.responseTopic);
            if (msg.isNoReturnAndNonBlocking()) {
                rpcService.sendRpcRequest(topic, msg);
            } else {
                var promise = new DefaultRpcPromise(rpcService.getExecutor());
                rpcService.sendRpcRequest(topic, msg, promise);
                return promise.get(30, TimeUnit.SECONDS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void receiveRpcRequest(MessageRequest request) {
        ProcessorManager.INSTANCE.process(request.getProcessorId(), HandlerFactory.INSTANCE.creatRPCHandler(request.getOrderId(), () -> {
            try {
                if (request.isNoReturnAndNonBlocking()){
                    registryHandler.invokeMethod(request);
                } else {
                    var response = registryHandler.invokeMethod(request);
                    response.setTopic(request.getResponseTopic());
                    rpcService.sendRpcResponse(response);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public void receiveRpcResponse(String messageId, MessageResponse response) {
        rpcService.receiveResponse(messageId, response);
    }

}
