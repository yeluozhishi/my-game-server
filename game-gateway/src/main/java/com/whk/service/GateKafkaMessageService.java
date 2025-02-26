package com.whk.service;

import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.user.UserMgr;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * 处理网关收到的消息
 */
@Service
@Slf4j
public class GateKafkaMessageService extends KafkaMessageService {

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) {
        var message = MessageInnerCoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(value -> {
            log.info("接受 server 信息" + value);
            UserMgr.INSTANCE.sendToClientMessage(value);
        });
    }
}
