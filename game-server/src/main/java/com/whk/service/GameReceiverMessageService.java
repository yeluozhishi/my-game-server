package com.whk.service;

import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.ReceiverMessageService;
import com.whk.rpc.consumer.proxy.RpcProxyHolder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class GameReceiverMessageService extends ReceiverMessageService {

    @Override
    @KafkaListener(topics = {"${eureka.instance.instance-id}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<byte[], byte[]> record) {
        var message = GameMessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(msg -> {
            logger.info("接受信息:" + msg);
            try {
                getDispatchGameMessageService().dealMessage(msg);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-request-game-message-topic}" + "-" + "${eureka.instance.instance-id}"}, groupId = "rpc-${game.kafka-topic.group-id}")
    public void consumeRpcRequestMessage(ConsumerRecord<byte[], byte[]> record) {
        var msgRpc = GameMessageInnerDecoder.INSTANCE.readRpcMessageRequest(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受信息RPCRequest:" + value);
            RpcProxyHolder.INSTANCE.receiveRpcRequest(value);
        });

    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-response-game-message-topic}" + "-" + "${eureka.instance.instance-id}"}, groupId = "rpc-request-${game.kafka-topic.group-id}")
    public void consumeRpcResponseMessage(ConsumerRecord<byte[], byte[]> record) {
        var msgRpc = GameMessageInnerDecoder.INSTANCE.readRpcMessageResponse(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受信息RPCResponse: " + value);
            RpcProxyHolder.INSTANCE.receiveRpcResponse(new String(record.key()), value);
        });
    }
}
