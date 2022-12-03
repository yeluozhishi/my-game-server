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
    public void consume(ConsumerRecord<byte[], byte[]> record) {
        var message = GameMessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(value -> {
            logger.info("接受 server 信息" + value);
            UserMgr.INSTANCE.sendToClientMessage(new String(record.key()), value);
        });
    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-request-game-message-topic}" + "-" + "${game.kafka-topic.server}"}, groupId = "rpc-${game.kafka-topic.group-id}")
    public void consumeRpcRequestMessage(ConsumerRecord<String, byte[]> record) {
        var msgRpc = GameMessageInnerDecoder.INSTANCE.readRpcMessageRequest(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受 RPCRequest 信息" + value);
            RpcProxyHolder.INSTANCE.receiveRpcRequest(value);
        });

    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-response-game-message-topic}" + "-" + "${game.kafka-topic.server}"}, groupId = "rpc-request-${game.kafka-topic.group-id}")
    public void consumeRpcResponseMessage(ConsumerRecord<byte[], byte[]> record) {
        var msgRpc = GameMessageInnerDecoder.INSTANCE.readRpcMessageResponse(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受 RPCResponse 信息" + value);
            RpcProxyHolder.INSTANCE.receiveRpcResponse(new String(record.key()), value);
        });
    }
}
