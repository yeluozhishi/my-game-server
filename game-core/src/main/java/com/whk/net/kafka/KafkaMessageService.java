package com.whk.net.kafka;

import com.whk.net.rpc.proxy.RpcProxyHolder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public abstract class KafkaMessageService {

    public Logger logger = Logger.getLogger(KafkaMessageService.class.getName());

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ProducerRecord<String, byte[]> producerRecord){
        kafkaTemplate.send(producerRecord);
    }

    public void init(){}

    /**
     * 消费
     * @param record 记录
     */
    public abstract void consume(ConsumerRecord<String, byte[]> record) throws InvocationTargetException, IllegalAccessException;


    @KafkaListener(topics = {"${game.kafka-topic.rpc-request-game-message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consumeRpcRequestMessage(ConsumerRecord<byte[], byte[]> record) {
        var msgRpc = MessageInnerCoder.INSTANCE.readRpcMessageRequest(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受 RPCRequest 信息" + value);
            RpcProxyHolder.INSTANCE.receiveRpcRequest(value);
        });

    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-response-game-message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consumeRpcResponseMessage(ConsumerRecord<byte[], byte[]> record) {
        var msgRpc = MessageInnerCoder.INSTANCE.readRpcMessageResponse(record.value());
        msgRpc.ifPresent(value -> {
            logger.info("接受信息RPCResponse: " + value);
            RpcProxyHolder.INSTANCE.receiveRpcResponse(new String(record.key()), value);
        });
    }
}
