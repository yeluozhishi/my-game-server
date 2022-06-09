package com.whk.net.kafkacoder;

import com.whk.net.Message;
import com.whk.net.serialize.CodeUtil;
import com.whk.rpc.serialize.MessageDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.Optional;

public enum GameMessageInnerDecoder {
    INSTANCE;

    private final CodeUtil codeUtil;

    GameMessageInnerDecoder(){
        codeUtil = new CodeUtil();
    }

    public void sendMessage(KafkaTemplate<String, byte[]> kafkaTemplate, Message message, String topic){
        try {
            for (String usename : message.getUserNames()) {
                var byteBuf = Unpooled.buffer();
                codeUtil.encode(byteBuf, message);
                ProducerRecord<String, byte[]> record = new ProducerRecord<>(topic, usename, byteBuf.array());
                kafkaTemplate.send(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Message> readGameMessagePackage(byte[] value){
        try {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(value);//直接使用byte[]包装为ByteBuf，减少一次数据复制
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
                return Optional.ofNullable((Message) codeUtil.decode(messageBody));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
