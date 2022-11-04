package com.whk.messageholder;

import com.whk.net.Message;
import com.whk.net.kafka.GameMessageInnerDecoder;
import org.springframework.kafka.core.KafkaTemplate;

public enum SendMessageHolder {
    INSTANCE;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    SendMessageHolder() {
    }

    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Message message){
        message.setComeFromClient(false);
        GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, message, message.getServerId());
    }



}
