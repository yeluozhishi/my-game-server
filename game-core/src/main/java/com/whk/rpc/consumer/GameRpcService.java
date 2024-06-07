package com.whk.rpc.consumer;

import com.whk.constant.TopicConstants;
import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRpcService {

    private static final Logger logger = LoggerFactory.getLogger(GameRpcService.class);

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

    public void sendRpcResponse(String serverId, MessageResponse msg) throws IOException {
        msg.setTopic(TopicConstants.RESPONSE_TOPIC.getTopic(serverId));
        GameMessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaMessageService, msg);
    }

    public void sendRpcRequest(String serverId, MessageRequest msg, Promise<Object> promise) throws IOException {
        msg.setMessageId(String.valueOf(seqId.getAndIncrement()));
        msg.setTopic(TopicConstants.REQUEST_TOPIC.getTopic(serverId));
        GameMessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaMessageService, msg);
        gameRpcCallbackService.addCallback(msg.getMessageId(), promise);
    }

    public void sendRpcRequest(String serverId, MessageRequest msg) throws IOException {
        msg.setMessageId(String.valueOf(seqId.getAndIncrement()));
        msg.setTopic(TopicConstants.REQUEST_TOPIC.getTopic(serverId));
        GameMessageInnerDecoder.INSTANCE.sendRpcMessage(kafkaMessageService, msg);
    }

    public void receiveResponse(String messageId, MessageResponse response) {
        gameRpcCallbackService.callback(messageId, response);
    }

    public EventExecutor getExecutor() {
        return gameRpcCallbackService.getEventExecutors().next();
    }

}
