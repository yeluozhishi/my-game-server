package com.whk.server;

import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.threadpool.HandlerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class GameKafkaMessageService extends KafkaMessageService {

    private DispatchProtocolService dispatchProtocolService;

    @Override
    public void init(){
        dispatchProtocolService = new DispatchProtocolService();
    }

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) {
        var message = MessageInnerCoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(msg -> {
            logger.info("接受信息:" + msg);
            try {
                dispatchProtocolService.dealMessage(msg.getMessage(), method -> HandlerFactory.INSTANCE.createPlayerHandler(msg.getMessage(), msg.getPlayerId(), method));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
