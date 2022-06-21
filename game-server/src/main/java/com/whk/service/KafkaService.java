package com.whk.service;

import com.whk.messageholder.SendMessageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        SendMessageHolder.INSTANCE.setKafkaTemplate(kafkaTemplate);
    }

}
