package com.whk.net.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import com.whk.net.rpc.serialize.ProtostuffSerializeUtil;
import com.whk.protobuf.message.MessageProto;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;

@Getter
public enum MessageInnerCoder {
    // 实例
    INSTANCE;

    private final ProtostuffSerializeUtil protostuffSerializeUtil;

    MessageInnerCoder() {
        protostuffSerializeUtil = new ProtostuffSerializeUtil();
    }

    public void sendMessage(KafkaMessageConsumeService kafkaMessageConsumeService, MessageProto.Message message, String topic) {
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, String.valueOf(message.getPlayerId()), message.toByteArray());
        kafkaMessageConsumeService.sendMessage(record);
    }

    public void sendRpcMessage(KafkaMessageConsumeService kafkaMessageConsumeService, MessageRequest message, String topic) {
        if (StringUtils.isEmpty(message.getResponseTopic())) return;
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, message.getMessageId(),
                protostuffSerializeUtil.encode(message).array());
        kafkaMessageConsumeService.sendMessage(record);
    }

    public void sendRpcMessage(KafkaMessageConsumeService kafkaMessageConsumeService, MessageResponse message) {
        if (StringUtils.isEmpty(message.getTopic())) return;
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(message.getTopic(), message.getMessageId(),
                protostuffSerializeUtil.encode(message).array());
        kafkaMessageConsumeService.sendMessage(record);
    }

    public MessageProto.Message readGameMessagePackage(byte[] value) throws InvalidProtocolBufferException {
        return readMessage(value, MessageProto.Message.class);
    }

    public MessageRequest readRpcMessageRequest(byte[] data) throws InvalidProtocolBufferException {
        return this.readMessage(data, MessageRequest.class);
    }

    public MessageResponse readRpcMessageResponse(byte[] data) throws InvalidProtocolBufferException {
        return this.readMessage(data, MessageResponse.class);
    }

    private <T> T readMessage(byte[] data, Class<T> c) throws InvalidProtocolBufferException {
        if (c == MessageProto.Message.class) {
            return (T) MessageProto.Message.parseFrom(data);
        } else {
            return protostuffSerializeUtil.decode(data, c);
        }
    }

}
