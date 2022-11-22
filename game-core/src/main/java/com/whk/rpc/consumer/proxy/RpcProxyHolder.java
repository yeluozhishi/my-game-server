package com.whk.rpc.consumer.proxy;

import com.whk.net.RPC.GameRpcService;
import com.whk.rpc.consumer.DefaultRPCPromise;
import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;
import com.whk.rpc.registry.RegistryHandler;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public enum RpcProxyHolder {
    INSTANCE;

    /**
     * rpc发送服务
     */
    private GameRpcService rpcService;

    private RegistryHandler registryHandler;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private ConcurrentHashMap<keys ,Object> rpcMap = new ConcurrentHashMap();

    private int TIME_OUT = 20;

    RpcProxyHolder(){
    }

    public void init(GameRpcService rpcService) {
        this.rpcService = rpcService;
        registryHandler = new RegistryHandler();
    }

    private record keys(String className, int serverId){}

    public <T> T getInstance(Class<?> clazz, int serverId){
        return (T) rpcMap.getOrDefault(new keys(clazz.getName(), serverId), RpcProxy.create(clazz, serverId));
    }

    public static void main(String[] args) {
        var r = new keys("11", 1);
        var t = new keys("11", 1);
        System.out.println(r.hashCode() == t.hashCode());
    }

    public Object sendRpcMessage(MessageRequest msg, boolean noReturn) {
        try{
            var promise = new DefaultRPCPromise(rpcService.getExecutor());
            if (noReturn){
                rpcService.sendRpcRequest(msg.getServerId(), msg, kafkaTemplate);
            } else {
                rpcService.sendRpcRequest(msg.getServerId(), msg, promise, kafkaTemplate);
            }
            return promise.get(TIME_OUT, TimeUnit.SECONDS);
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void receiveRPCRequest(MessageRequest request){
        try {
            var response = registryHandler.invokeMethod(request);
            rpcService.sendRpcResponse(request.getServerId(), response, kafkaTemplate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveRPCResponse(String messageId,MessageResponse response){
        rpcService.receiveResponse(messageId, response);
    }

} 
