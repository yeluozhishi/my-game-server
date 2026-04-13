package com.whk.net.rpc.consumer;

import com.whk.net.kafka.KafkaMessageConsumeService;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.atomic.AtomicInteger;

public class GameRpcService {

    /**
     * 自增的唯一序列Id
     */
    private final AtomicInteger seqId = new AtomicInteger();

    private final GameRpcCallbackService gameRpcCallbackService;

    private final KafkaMessageConsumeService kafkaMessageConsumeService;

    public GameRpcService(KafkaMessageConsumeService kafkaMessageConsumeService) {
        this.gameRpcCallbackService = new GameRpcCallbackService();
        this.kafkaMessageConsumeService = kafkaMessageConsumeService;
    }

    public void sendRpcResponse(MessageResponse msg) {
        MessageInnerCoder.INSTANCE.sendRpcMessage(kafkaMessageConsumeService, msg);
    }

    public void sendRpcRequest(String topic, MessageRequest msg, Promise<Object> promise) {
        msg.setMessageId(String.valueOf(seqId.getAndIncrement()));
        MessageInnerCoder.INSTANCE.sendRpcMessage(kafkaMessageConsumeService, msg, topic);
        gameRpcCallbackService.addCallback(msg.getMessageId(), promise);
    }

    public void sendRpcRequest(String topic, MessageRequest msg) {
        msg.setMessageId(String.valueOf(seqId.getAndIncrement()));
        MessageInnerCoder.INSTANCE.sendRpcMessage(kafkaMessageConsumeService, msg, topic);
    }

    public void receiveResponse(String messageId, MessageResponse response) {
        gameRpcCallbackService.callback(messageId, response);
    }

    public EventExecutor getExecutor() {
        return gameRpcCallbackService.getEventExecutors().next();
    }

}
