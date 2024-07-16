package com.whk.net.rpc.consumer.proxy;

import com.whk.net.rpc.registry.RegistryHandler;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.consumer.DefaultRpcPromise;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import lombok.Getter;

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

    private final ConcurrentHashMap<String, IRpcService> rpcMap = new ConcurrentHashMap<>();

    @Getter
    private String topic;

    private final int TIME_OUT = 30;

    RpcProxyHolder() {
    }

    public void init(GameRpcService rpcService, String topic, String rpcPosition) {
        this.rpcService = rpcService;
        this.topic = topic;
        registryHandler = new RegistryHandler(rpcPosition);
        logger.warning("rpc 初始化完成！");
    }



    public IRpcService getInstance(Class<?> clazz, String topic) {
        var key = clazz.getName() + topic;
        if (rpcMap.containsKey(key)) {
            return rpcMap.get(key);
        } else {
            var object = (IRpcService) RpcProxy.create(clazz, topic);
            rpcMap.put(key, object);
            return object;
        }
    }

    public Object sendRpcMessage(MessageRequest msg) {
        try {
            // 替换topic, 接收方可以用topic返回消息
            var topic = msg.getTargetTopic();
            msg.setTargetTopic(this.topic);
            if (msg.isNoReturnAndNonBlocking()) {
                rpcService.sendRpcRequest(topic, msg);
            } else {
                var promise = new DefaultRpcPromise(rpcService.getExecutor());
                rpcService.sendRpcRequest(topic, msg, promise);
                return promise.get(TIME_OUT, TimeUnit.SECONDS);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void receiveRpcRequest(MessageRequest request) {
        try {
            var response = registryHandler.invokeMethod(request);
            rpcService.sendRpcResponse(request.getTargetTopic(), response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveRpcResponse(String messageId, MessageResponse response) {
        rpcService.receiveResponse(messageId, response);
    }

}
