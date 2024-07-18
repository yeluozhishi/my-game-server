package com.whk.service;

import com.whk.net.kafka.MessageInnerDecoder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.consumer.proxy.RpcProxyHolder;
import com.whk.user.UserMgr;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * 处理网关收到的消息
 */
@Service
public class GateKafkaMessageService extends KafkaMessageService {

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<byte[], byte[]> record) {
        var message = MessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(value -> {
            logger.info("接受 server 信息" + value);
            UserMgr.INSTANCE.sendToClientMessage(value);
        });
    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-request-game-message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consumeRpcRequestMessage(ConsumerRecord<String, byte[]> record) {
        var msgRpc = MessageInnerDecoder.INSTANCE.readRpcMessageRequest(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受 RPCRequest 信息" + value);
            RpcProxyHolder.INSTANCE.receiveRpcRequest(value);
        });

    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-response-game-message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consumeRpcResponseMessage(ConsumerRecord<byte[], byte[]> record) {
        var msgRpc = MessageInnerDecoder.INSTANCE.readRpcMessageResponse(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受 RPCResponse 信息" + value);
            RpcProxyHolder.INSTANCE.receiveRpcResponse(new String(record.key()), value);
        });
    }
}
