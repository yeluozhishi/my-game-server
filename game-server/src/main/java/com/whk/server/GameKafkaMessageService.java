package com.whk.server;

import com.whk.threadpool.dispatchprotocol.DispatchProtocolService;
import com.whk.net.kafka.MessageInnerDecoder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.threadpool.messagehandler.MessageHandlerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class GameKafkaMessageService extends KafkaMessageService {

    private DispatchProtocolService dispatchProtocolService;

    @Override
    public void init(){
        dispatchProtocolService = new DispatchProtocolService();
    }

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<byte[], byte[]> record) {
        var message = MessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(msg -> {
            logger.info("接受信息:" + msg);
            try {
                dispatchProtocolService.dealMessage(msg.getMessage(), msg.getPlayerId(),
                        method -> MessageHandlerFactory.INSTANCE.createPlayerEvent(msg.getMessage(), msg.getPlayerId(), method));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-request-game-message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consumeRpcRequestMessage(ConsumerRecord<byte[], byte[]> record) {
        var msgRpc = MessageInnerDecoder.INSTANCE.readRpcMessageRequest(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受信息RPCRequest:" + value);
            RpcProxyHolder.INSTANCE.receiveRpcRequest(value);
        });

    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-response-game-message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consumeRpcResponseMessage(ConsumerRecord<byte[], byte[]> record) {
        var msgRpc = MessageInnerDecoder.INSTANCE.readRpcMessageResponse(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受信息RPCResponse: " + value);
            if (Objects.nonNull(record.key())) RpcProxyHolder.INSTANCE.receiveRpcResponse(new String(record.key()), value);
        });
    }
}
