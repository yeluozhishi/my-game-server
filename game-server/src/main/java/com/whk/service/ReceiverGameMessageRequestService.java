package com.whk.service;

import com.whk.net.dispatchmessage.DispatchGameMessageService;
import com.whk.net.kafkacoder.GameMessageInnerDecoder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

@Service
public class ReceiverGameMessageRequestService {

    private final Logger logger = Logger.getLogger(ReceiverGameMessageRequestService.class.getName());

    private DispatchGameMessageService dispatchGameMessageService;

    @Autowired
    public void setDispatchGameMessageService(DispatchGameMessageService dispatchGameMessageService) {
        this.dispatchGameMessageService = dispatchGameMessageService;
    }

    @KafkaListener(topics = {"${game.kafka-topic.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) throws InvocationTargetException, IllegalAccessException {
        var message = GameMessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        if (message.isPresent()){
            logger.info("接受网关信息" + message.get());
            if (message.get().getToServerId() == 1){
                dispatchGameMessageService.dealMessage(message.get());
            }
        }
    }

}
