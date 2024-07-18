package com.whk.net.kafka;

import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import com.whk.net.rpc.serialize.ProtostuffSerializeUtil;
import com.whk.protobuf.message.MessageWrapperProto;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Optional;

public enum MessageInnerDecoder {
    // 实例
    INSTANCE;

    private final ProtostuffSerializeUtil protostuffSerializeUtil;

    MessageInnerDecoder() {
        protostuffSerializeUtil = new ProtostuffSerializeUtil();
    }

    public ProtostuffSerializeUtil getProtostuffSerializeUtil() {
        return protostuffSerializeUtil;
    }

    public void sendMessage(KafkaMessageService kafkaMessageService, MessageWrapperProto.MessageWrapper message, String topic) throws IOException {
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, String.valueOf(message.getPlayerId()), message.toByteArray());
        kafkaMessageService.sendMessage(record);
    }

    public void sendRpcMessage(KafkaMessageService kafkaMessageService, MessageRequest message, String topic) throws IOException {
        if (message.getTargetTopic() == null || message.getTargetTopic().isBlank()) return;
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, message.getMessageId(),
                protostuffSerializeUtil.encode(message).array());
        kafkaMessageService.sendMessage(record);
    }

    public void sendRpcMessage(KafkaMessageService kafkaMessageService, MessageResponse message, String topic) throws IOException {
        if (message.getTopic() == null || message.getTopic().isBlank()) return;
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, message.getMessageId(),
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
        try {

            if (c == MessageWrapperProto.MessageWrapper.class) {
                return (Optional<T>) Optional.ofNullable(MessageWrapperProto.MessageWrapper.parseFrom(data));
            } else {
                return protostuffSerializeUtil.decode(data, c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
