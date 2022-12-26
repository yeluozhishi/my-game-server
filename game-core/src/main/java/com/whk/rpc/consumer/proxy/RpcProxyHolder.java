package com.whk.rpc.consumer.proxy;

import com.whk.rpc.consumer.GameRpcService;
import com.whk.rpc.consumer.DefaultRpcPromise;
import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;
import com.whk.rpc.registry.RegistryHandler;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
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

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private ConcurrentHashMap<keys ,Object> rpcMap = new ConcurrentHashMap();

    private String instanceId;

    private final int TIME_OUT = 30;

    RpcProxyHolder(){
    }

    public void init(GameRpcService rpcService, String instanceId, KafkaTemplate<String, byte[]> kafkaTemplate, String rpcPosition) {
        this.rpcService = rpcService;
        this.instanceId = instanceId;
        registryHandler = new RegistryHandler(rpcPosition);
        this.kafkaTemplate = kafkaTemplate;
        logger.warning("rpc 初始化完成！");
    }

    private record keys(String className, String instanceId){}

    public <T> T getInstance(Class<?> clazz, String instanceId){
        return (T) rpcMap.getOrDefault(new keys(clazz.getName(), instanceId), RpcProxy.create(clazz, instanceId));
    }

    public Object sendRpcMessage(MessageRequest msg) {
        try{
            var promise = new DefaultRpcPromise(rpcService.getExecutor());
            // 替换serverId, 接收方可以用serverId，返回消息
            var instance = msg.getInstanceId();
            msg.setInstanceId(instanceId);
            if (msg.isNoReturnAndNonBlocking()){
                rpcService.sendRpcRequest(instance, msg, kafkaTemplate);
            } else {
                rpcService.sendRpcRequest(instance, msg, promise, kafkaTemplate);
                return promise.get(TIME_OUT, TimeUnit.SECONDS);
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void receiveRpcRequest(MessageRequest request){
        try {
            var response = registryHandler.invokeMethod(request);
            rpcService.sendRpcResponse(request.getInstanceId(), response, kafkaTemplate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveRpcResponse(String messageId, MessageResponse response){
        rpcService.receiveResponse(messageId, response);
    }

    public String getInstanceId() {
        return instanceId;
    }
}
