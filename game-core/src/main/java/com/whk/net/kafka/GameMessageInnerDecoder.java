package com.whk.net.kafka;

import com.whk.net.serialize.CodeUtil;
import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;
import com.whk.rpc.serialize.MessageDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.whk.message.Message;

import java.io.IOException;
import java.util.Optional;

public enum GameMessageInnerDecoder {
    // 实例
    INSTANCE;

    private final CodeUtil codeUtil;

    GameMessageInnerDecoder() {
        codeUtil = new CodeUtil();
    }

    public void sendMessage(KafkaTemplate<String, byte[]> kafkaTemplate, Message message, String topic) {
        if (topic == null || topic.isBlank()) return;
        try {
            var byteBuf = Unpooled.buffer();
            codeUtil.encode(byteBuf, message);
            ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, message.getPlayerId().toString(), byteBuf.array());
            kafkaTemplate.send(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRpcMessage(KafkaTemplate<String, byte[]> kafkaTemplate, MessageRequest message, String topic) throws IOException {
        var byteBuf = Unpooled.buffer();
        codeUtil.encodeRpc(byteBuf, message);
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, String.valueOf(message.getMessageId()), byteBuf.array());
        kafkaTemplate.send(record);
    }

    public void sendRpcMessage(KafkaTemplate<String, byte[]> kafkaTemplate, MessageResponse message, String topic) throws IOException {
        var byteBuf = Unpooled.buffer();
        codeUtil.encodeRpc(byteBuf, message);
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, message.getMessageId(), byteBuf.array());
        kafkaTemplate.send(record);
    }

    public Optional<Message> readGameMessagePackage(byte[] value) {
        return readMessage(value, Message.class);
    }

    public Optional<MessageRequest> readRpcMessageRequest(byte[] data) {
        return this.readMessage(data, MessageRequest.class);
    }

    public Optional<MessageResponse> readRpcMessageResponse(byte[] data) {
        return this.readMessage(data, MessageResponse.class);
    }

    private  <T> Optional<T> readMessage(byte[] data, Class c) {
        try {
            //直接使用byte[]包装为ByteBuf，减少一次数据复制
            ByteBuf byteBuf = Unpooled.wrappedBuffer(data);
            if (byteBuf.readableBytes() < MessageDecoder.MESSAGE_LENGTH) {
                return Optional.empty();
            }

            byteBuf.markReaderIndex();
            int messageLength = byteBuf.readInt();

            if (messageLength < 0) {
                return Optional.empty();
            }

            if (byteBuf.readableBytes() < messageLength) {
                byteBuf.resetReaderIndex();
                return Optional.empty();
            } else {
                byte[] messageBody = new byte[messageLength];
                byteBuf.readBytes(messageBody);
                if (c == Message.class){
                    return Optional.ofNullable((T) codeUtil.decode(messageBody, c));
                } else {
                    return Optional.ofNullable((T) codeUtil.decodeRpc(messageBody, c));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
