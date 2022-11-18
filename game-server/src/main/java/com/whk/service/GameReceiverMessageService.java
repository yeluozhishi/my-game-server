package com.whk.service;

import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.ReceiverMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

@Service
public class GameReceiverMessageService extends ReceiverMessageService {
    @Override
    @KafkaListener(topics = {"${game.kafka-topic.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) {
        var message = GameMessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(msg -> {
            logger.info("接受信息" + msg);
            try {
                getDispatchGameMessageService().dealMessage(msg);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
