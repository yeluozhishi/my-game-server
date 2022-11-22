package com.whk.service;

import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.ReceiverMessageService;
import com.whk.rpc.consumer.proxy.RpcProxyHolder;
import com.whk.user.UserMgr;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * 处理网关收到的消息
 */
@Service
public class GateReceiverMessageService extends ReceiverMessageService {

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) {
        var message = GameMessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(value -> {
            logger.info("接受 server 信息" + value);
            UserMgr.INSTANCE.sendToClientMessage(record.key(), value);
        });
    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-request-game-message-topic}" + "-" + "${game.kafka-topic.server}"}, groupId = "rpc-${game.kafka-topic.group-id}")
    public void consumeRPCRequestMessage(ConsumerRecord<String, byte[]> record) {
        var msgRPC = GameMessageInnerDecoder.INSTANCE.readRPCMessageRequest(record.value());
        msgRPC.ifPresent(value -> {
            RpcProxyHolder.INSTANCE.receiveRPCRequest(value);
        });

    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-response-game-message-topic}" + "-" + "${game.kafka-topic.server}"}, groupId = "rpc-request-${game.kafka-topic.group-id}")
    public void consumeRPCResponseMessage(ConsumerRecord<String, byte[]> record) {
        var msgRPC = GameMessageInnerDecoder.INSTANCE.readRPCMessageResponse(record.value());
        msgRPC.ifPresent(value -> {
            RpcProxyHolder.INSTANCE.receiveRPCResponse(record.key(), value);
        });
    }
}
