package com.whk.rpc.consumer;

import com.whk.rpc.api.IRpcService;
import com.whk.rpc.api.provider.RpcServiceImpl;
import com.whk.rpc.consumer.proxy.RpcProxy;
import com.whk.rpc.consumer.proxy.RpcProxyHolder;
import com.whk.rpc.consumer.server.Server;
import com.whk.rpc.consumer.server.ServerManager;
import com.whk.rpc.serialize.RpcSerializeProtocol;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcConsumer {

    public RpcServerLoader rpcServerLoader;

    public ServerManager serverManager;

    public void start() {
        rpcServerLoader = RpcServerLoader.getInstance();
        serverManager = ServerManager.getInstance();
//        for (int i = 1; i < 11; i++) {
//            var port = 8080 + i;
            serverManager.addServer(new Server(1, "127.0.0.1", 8080));
//        }
//        serverManager.addServer(new Server(1, "172.29.88.149", 8081));
        rpcServerLoader.setInfo(RpcSerializeProtocol.PROTOSTUFFSERIALIZE);
        serverManager.getServerMap().values().forEach(f -> {
            try {
                rpcServerLoader.load(f, "");
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        var now = System.currentTimeMillis();
        var rp = new RpcConsumer();
        rp.start();

        System.out.println("启动成功");
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 1; i++) {
            rp.t(executorService);
        }
        System.out.println(System.currentTimeMillis() - now);
    }

    public void t(ExecutorService executorService) {
        executorService.execute(() -> {
            var now = System.currentTimeMillis();
            RpcServiceImpl service = new RpcServiceImpl();
            service.add(1, 2);

            IRpcService service1 = RpcProxyHolder.INSTANCE.getInstance(IRpcService.class, 1);
            service1.add(1,2);
            System.out.println("use time: " + (System.currentTimeMillis() - now));
        });
    }

}
