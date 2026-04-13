package com.whk.net;

import com.whk.CmdToMessageUtil;
import com.whk.net.kafka.KafkaMessageConsumeService;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.threadpool.handler.HandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class GameKafkaMessageConsumeService extends KafkaMessageConsumeService {

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) {
        try {
            var msg = MessageInnerCoder.INSTANCE.readGameMessagePackage(record.value());
            log.info("接受信息:" + msg);
            var body = CmdToMessageUtil.getInstance().parsePayload(msg);
            getDispatchProtocolService().dealMessage(msg.getCommand(), method ->
                    HandlerFactory.INSTANCE.createPlayerHandler(body, msg.getPlayerId(), method));
        } catch (Exception e) {
            log.error("处理信息异常:{}, {}", e, e.getStackTrace());
        }
    }

}
