package com.whk.net.kafka;

import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import com.whk.net.rpc.serialize.CodeUtil;
import com.whk.protobuf.message.MessageWrapperProto;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Optional;

public enum GameMessageInnerDecoder {
    // 实例
    INSTANCE;

    private final CodeUtil codeUtil;

    GameMessageInnerDecoder() {
        codeUtil = new CodeUtil();
    }

    public void sendMessage(KafkaMessageService kafkaMessageService, MessageWrapperProto.MessageWrapper message, String instanceId) throws IOException {
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(instanceId, String.valueOf(message.getPlayerId()), message.toByteArray());
        kafkaMessageService.sendMessage(record);

    }

    public void sendRpcMessage(KafkaMessageService kafkaMessageService, MessageRequest message) throws IOException {
        if (message.getTopic() == null || message.getTopic().isBlank()) return;
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(message.getTopic(), message.getMessageId(),
                codeUtil.encodeRpc(message).array());
        kafkaMessageService.sendMessage(record);
    }

    public void sendRpcMessage(KafkaMessageService kafkaMessageService, MessageResponse message) throws IOException {
        if (message.getTopic() == null || message.getTopic().isBlank()) return;
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(message.getTopic(), message.getMessageId(),
                codeUtil.encodeRpc(message).array());
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
                return Optional.ofNullable(codeUtil.decode(data));
            } else {
                return codeUtil.decodeRpc(data, c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
