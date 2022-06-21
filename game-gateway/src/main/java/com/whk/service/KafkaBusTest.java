package com.whk.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

//@Service
public class KafkaBusTest {

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void init(){
        String s = "hello,kafka";
        ProducerRecord<String, byte[]> record = new ProducerRecord<>("TestTopic", "hello", s.getBytes());
        kafkaTemplate.send(record);
    }

    @KafkaListener(topics = "TestTopic", groupId = "game_gate")
    public void receiver(ConsumerRecord<String, byte[]> record){
        System.out.println(new String(record.value()));
    }

}
