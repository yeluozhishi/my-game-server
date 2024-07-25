package com.whk.net.rpc.consumer;

import com.whk.net.kafka.MessageInnerDecoder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRpcService {

    /**
     * 自增的唯一序列Id
     */
    private final AtomicInteger seqId = new AtomicInteger();

    private final GameRpcCallbackService gameRpcCallbackService;

    private final KafkaMessageService kafkaMessageService;

    public GameRpcService(DefaultEventExecutorGroup eventExecutors, KafkaMessageService kafkaMessageService) {
        this.gameRpcCallbackService = new GameRpcCallbackService(eventExecutors);
        this.kafkaMessageService = kafkaMessageService;
    }

    public void sendRpcResponse(MessageResponse msg) throws IOException {
        MessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaMessageService, msg);
    }

    public void sendRpcRequest(String topic, MessageRequest msg, Promise<Object> promise) throws IOException {
        msg.setMessageId(String.valueOf(seqId.getAndIncrement()));
        MessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaMessageService, msg, topic);
        gameRpcCallbackService.addCallback(msg.getMessageId(), promise);
    }

    public void sendRpcRequest(String topic, MessageRequest msg) throws IOException {
        msg.setMessageId(String.valueOf(seqId.getAndIncrement()));
        MessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaMessageService, msg, topic);
    }

    public void receiveResponse(String messageId, MessageResponse response) {
        gameRpcCallbackService.callback(messageId, response);
    }

    public EventExecutor getExecutor() {
        return gameRpcCallbackService.getEventExecutors().next();
    }

}
