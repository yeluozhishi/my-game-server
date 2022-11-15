package com.whk.service;

import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.ReceiverMessageService;
import com.whk.user.UserMgr;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * 其他服务器连接网关 消息处理
 */
@Service
public class GateReceiverMessageService extends ReceiverMessageService {

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) throws InvocationTargetException, IllegalAccessException {
        var message = GameMessageInnerDecoder.INSTANCE.readGameMessagePackage(record.value());
        if (message.isPresent()){
            logger.info("接受信息" + message.get());
            var user = UserMgr.INSTANCE.getUserByPlayerId(message.get().getPlayerId());
            user.ifPresent(value -> value.sendToClientMessage(message.get()));
        }
    }
}
