package com.whk.service;

import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.ReceiverMessageService;
import com.whk.user.UserMgr;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

@Service
public class GateReceiverMessageService extends ReceiverMessageService {
    private final Logger logger = Logger.getLogger(GateReceiverMessageService.class.getName());

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) throws InvocationTargetException, IllegalAccessException {
        var message = GameMessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        if (message.isPresent()){
            logger.info("接受信息" + message.get());
            if (message.get().getComeFromClient()){
                if (message.get().getToServerId() == 1){
                    getDispatchGameMessageService().dealMessage(message.get());
                }
            } else {
                message.get().getUserNames().forEach(name -> {
                    var user = UserMgr.INSTANCE.getUser(name);
                    if (user.isPresent()){
                        user.get().sendToClientMessage(message.get());
                    }
                });
            }
        }
    }
}
