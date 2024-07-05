package com.whk.net.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public abstract class KafkaMessageService {

    public Logger logger = Logger.getLogger(KafkaMessageService.class.getName());

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ProducerRecord<String, byte[]> producerRecord){
        kafkaTemplate.send(producerRecord);
    }

    /**
     * 消费
     * @param record 记录
     */
    public abstract void consume(ConsumerRecord<byte[], byte[]> record) throws InvocationTargetException, IllegalAccessException;
}
