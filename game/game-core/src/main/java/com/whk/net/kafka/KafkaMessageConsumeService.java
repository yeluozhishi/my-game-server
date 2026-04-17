package com.whk.net.kafka;

import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Getter
public abstract class KafkaMessageConsumeService {

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private boolean init = false;

    private DispatchProtocolService dispatchProtocolService;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ProducerRecord<String, byte[]> producerRecord) {
        if (init) kafkaTemplate.send(producerRecord);
    }

    public void init(DispatchProtocolService dispatchProtocolService) {
        this.dispatchProtocolService = dispatchProtocolService;
        init = true;
    }

    public void destroy() {
        kafkaTemplate.destroy();
        init = false;
    }

    /**
     * 消费
     *
     * @param record 记录
     */
    public abstract void consume(ConsumerRecord<String, byte[]> record) throws InvocationTargetException, IllegalAccessException;


    @KafkaListener(topics = {"${game.kafka-topic.rpc-request-message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consumeRpcRequestMessage(ConsumerRecord<byte[], byte[]> record) {
        try {
            var msgRpc = MessageInnerCoder.INSTANCE.readRpcMessageRequest(record.value());
            log.info("接受 RPCRequest 信息" + msgRpc);
            RpcProxyHolder.INSTANCE.receiveRpcRequest(msgRpc);
        } catch (Exception e) {
            log.error("接受 RPCRequest 信息失败:{}, {}", e.getMessage(), e.getStackTrace());
        }
    }

    @KafkaListener(topics = {"${game.kafka-topic.rpc-response-message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consumeRpcResponseMessage(ConsumerRecord<byte[], byte[]> record) {
        try {
            var msgRpc = MessageInnerCoder.INSTANCE.readRpcMessageResponse(record.value());
            log.info("接受信息RPCResponse: " + msgRpc);
            RpcProxyHolder.INSTANCE.receiveRpcResponse(new String(record.key()), msgRpc);
        } catch (Exception e) {
            log.error("接受 RPCResponse 信息失败:{}, {}", e.getMessage(), e.getStackTrace());
        }
    }
}
