package com.whk.net.rpc.proxy;

import com.whk.net.rpc.consumer.DefaultRpcPromise;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import com.whk.net.rpc.registry.RegistryHandler;
import com.whk.threadpool.handler.HandlerFactory;
import com.whk.threadpool.processor.ProcessorManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public enum RpcProxyHolder {
    // 实例
    INSTANCE;

    /**
     * rpc发送服务
     */
    private GameRpcService rpcService;

    private RegistryHandler registryHandler;

    private String responseTopic;

    RpcProxyHolder() {
    }

    public void init(GameRpcService rpcService, String responseTopic) {
        this.rpcService = rpcService;
        this.responseTopic = responseTopic;
        registryHandler = new RegistryHandler();
        log.info("rpc 初始化完成！");
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
        } catch (Exception e) {
            log.error("%s; %s".formatted(e.getMessage(), Arrays.toString(e.getStackTrace())));
        }
        return null;
    }


    public void receiveRpcRequest(MessageRequest request) {
        ProcessorManager.INSTANCE.process(HandlerFactory.INSTANCE.creatRPCHandler(request));
    }

    public void receiveRpcResponse(String messageId, MessageResponse response) {
        rpcService.receiveResponse(messageId, response);
    }

}
