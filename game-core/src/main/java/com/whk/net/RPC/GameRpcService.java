package com.whk.net.RPC;

import com.whk.constant.TopicConstants;
import com.whk.net.enity.MapBeanServer;
import com.whk.net.kafka.GameMessageInnerDecoder;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRpcService {

    private static Logger logger = LoggerFactory.getLogger(GameRpcService.class);

    private AtomicInteger seqId = new AtomicInteger();// 自增的唯一序列Id
    private int localServerId;// 本地服务实例ID

    private EventExecutorGroup eventExecutorGroup;

    private GameRpcCallbackService gameRpcCallbackService;

    public GameRpcService(int localServerId, EventExecutorGroup eventExecutorGroup) {
        this.localServerId = localServerId;
        this.eventExecutorGroup = eventExecutorGroup;
        this.gameRpcCallbackService = new GameRpcCallbackService(eventExecutorGroup);
    }

    public void sendRPCResponse(MapBeanServer msg, KafkaTemplate<String, byte[]> kafkaTemplate) throws IOException {
        MapBeanServer msgClone = msg.clone();
        String sendTopic = TopicConstants.RESPONSE_TOPIC.getTopic(msg.getServerId());
        GameMessageInnerDecoder.INSTANCE.sendRPCMessage(kafkaTemplate, msgClone, sendTopic);
    }

    public void sendRPCRequest(MapBeanServer msg, Promise<MapBeanServer> promise, KafkaTemplate<String, byte[]> kafkaTemplate) throws IOException {
        MapBeanServer msgClone = msg.clone();
        msgClone.setSeqId(seqId.getAndIncrement());
        String sendTopic = TopicConstants.REQUEST_TOPIC.getTopic(msg.getServerId());
        GameMessageInnerDecoder.INSTANCE.sendRPCMessage(kafkaTemplate, msgClone, sendTopic);
        gameRpcCallbackService.addCallback(msgClone.getSeqId(), promise);
    }

    public void receiveResponse(MapBeanServer mapBeanServer){
        gameRpcCallbackService.callback(mapBeanServer);
    }

}
