package com.whk.rpc.consumer;

import com.whk.rpc.api.IRpcService;
import com.whk.rpc.consumer.proxy.RpcProxy;
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
            rp.t(executorService, i + 1);
        }
        System.out.println(System.currentTimeMillis() - now);
    }

    public void t(ExecutorService executorService, int finaI) {
        executorService.execute(() -> {
            var now = System.currentTimeMillis();
            for (int i = 1; i < 1; i++) {

//                IRpcHelloService rpcHello = RpcProxy.create(IRpcHelloService.class, 1);
//                rpcHello.hello("hahaha");
                IRpcService service = RpcProxy.create(IRpcService.class, 1);
                service.add(8, 2);
                service.sub(8, 2);
                service.mult(8, 2);
                service.div(8, 2);
            }
            System.out.println("use time: " + (System.currentTimeMillis() - now));
        });
    }

}
