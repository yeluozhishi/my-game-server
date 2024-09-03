package com.whk.net.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.whk.StringUtils;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import com.whk.net.rpc.serialize.ProtostuffSerializeUtil;
import com.whk.protobuf.message.MessageWrapperProto;
import lombok.Getter;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Optional;

@Getter
public enum MessageInnerDecoder {
    // 实例
    INSTANCE;

    private final ProtostuffSerializeUtil protostuffSerializeUtil;

    MessageInnerDecoder() {
        protostuffSerializeUtil = new ProtostuffSerializeUtil();
    }

    public void sendMessage(KafkaMessageService kafkaMessageService, MessageWrapperProto.MessageWrapper message, String topic) throws IOException {
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, String.valueOf(message.getPlayerId()), message.toByteArray());
        kafkaMessageService.sendMessage(record);
    }

    public void sendRpcMessage(KafkaMessageService kafkaMessageService, MessageRequest message, String topic) {
        if (StringUtils.isEmpty(message.getResponseTopic())) return;
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, message.getMessageId(),
                protostuffSerializeUtil.encode(message).array());
        kafkaMessageService.sendMessage(record);
    }

    public void sendRpcMessage(KafkaMessageService kafkaMessageService, MessageResponse message) {
        if (StringUtils.isEmpty(message.getTopic())) return;
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(message.getTopic(), message.getMessageId(),
                protostuffSerializeUtil.encode(message).array());
        kafkaMessageService.sendMessage(record);
    }

    public Optional<MessageWrapperProto.MessageWrapper> readGameMessagePackage(byte[] value) {
        return readMessage(value, MessageWrapperProto.MessageWrapper.class);
    }

    public Optional<MessageRequest> readRpcMessageRequest(byte[] data) {
        return this.readMessage(data, MessageRequest.class);
    }

    public Optional<MessageResponse> readRpcMessageResponse(byte[] data) {
        return this.readMessage(data, MessageResponse.class);
    }

    private <T> Optional<T> readMessage(byte[] data, Class<T> c) {
        if (c == MessageWrapperProto.MessageWrapper.class) {
            try {
                return (Optional<T>) Optional.ofNullable(MessageWrapperProto.MessageWrapper.parseFrom(data));
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        } else {
            return protostuffSerializeUtil.decode(data, c);
        }
    }

}
