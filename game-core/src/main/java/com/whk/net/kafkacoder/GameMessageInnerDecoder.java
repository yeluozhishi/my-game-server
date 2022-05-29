package com.whk.net.kafkacoder;

import com.whk.net.Message;
import com.whk.net.serialize.CodeUtil;
import io.netty.buffer.Unpooled;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.Optional;

public enum GameMessageInnerDecoder {
    INSTANCE;

    private CodeUtil codeUtil;

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
            return Optional.ofNullable((Message) codeUtil.decode(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
