package com.whk.net.RPC;

import com.whk.constant.TopicConstants;
import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRpcService {

    private static final Logger logger = LoggerFactory.getLogger(GameRpcService.class);

    /**
     * 自增的唯一序列Id
     */
    private final AtomicInteger seqId = new AtomicInteger();

    private final EventExecutorGroup eventExecutorGroup;

    private final GameRpcCallbackService gameRpcCallbackService;


    public GameRpcService(EventExecutorGroup eventExecutorGroup) {
        this.eventExecutorGroup = eventExecutorGroup;
        this.gameRpcCallbackService = new GameRpcCallbackService(eventExecutorGroup);
    }

    public void sendRpcResponse(int serverId, MessageResponse msg, KafkaTemplate<String, byte[]> kafkaTemplate) throws IOException {
        String sendTopic = TopicConstants.RESPONSE_TOPIC.getTopic(serverId);
        GameMessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaTemplate, msg, sendTopic);
    }

    public void sendRpcRequest(int serverId, MessageRequest msg, Promise<MessageResponse> promise, KafkaTemplate<String, byte[]> kafkaTemplate) throws IOException {
        msg.setMessageId(String.valueOf(seqId.getAndIncrement()));
        String sendTopic = TopicConstants.REQUEST_TOPIC.getTopic(serverId);
        GameMessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaTemplate, msg, sendTopic);
        gameRpcCallbackService.addCallback(msg.getMessageId(), promise);
    }

    public void sendRpcRequest(int serverId, MessageRequest msg, KafkaTemplate<String, byte[]> kafkaTemplate) throws IOException {
        msg.setMessageId(String.valueOf(seqId.getAndIncrement()));
        String sendTopic = TopicConstants.REQUEST_TOPIC.getTopic(serverId);
        GameMessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaTemplate, msg, sendTopic);
    }

    public void receiveResponse(String messageId,MessageResponse response){
        gameRpcCallbackService.callback(messageId, response);
    }

    public EventExecutor getExecutor(){
        return eventExecutorGroup.next();
    }

}
