package com.whk.service;

import com.whk.net.enity.EnumMessageType;
import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.ReceiverMessageService;
import com.whk.user.UserMgr;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

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
            UserMgr.INSTANCE.sendMessageToClient(value);
        });
    }

    @KafkaListener(topics = {"${game.channel.rpc-request-game-message-topic}" + "-" + "${game.server.config.server-id}"}, groupId = "rpc-${game.channel.topic-group-id}")
    public void consumeRPCRequestMessage(ConsumerRecord<String, byte[]> record) {
        var msgRPC = GameMessageInnerDecoder.INSTANCE.readRPCMessage(record.value());
        msgRPC.ifPresent(value -> {
            UserMgr.INSTANCE.sendRPCMessage(EnumMessageType.RPC_REQUEST, value);
        });

    }

    @KafkaListener(topics = {"${game.channel.rpc-response-game-message-topic}" + "-" + "${game.server.config.server-id}"}, groupId = "rpc-request-${game.channel.topic-group-id}")
    public void consumeRPCResponseMessage(ConsumerRecord<String, byte[]> record) {
        var msgRPC = GameMessageInnerDecoder.INSTANCE.readRPCMessage(record.value());
        msgRPC.ifPresent(value -> {
            UserMgr.INSTANCE.receiveRPCMessage(value);
        });
    }
}
