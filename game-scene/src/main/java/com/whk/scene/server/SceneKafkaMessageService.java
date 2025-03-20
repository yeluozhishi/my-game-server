package com.whk.scene.server;

import com.whk.CmdToMessageUtil;
import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.scene.actor.PlayerActorMgr;
import com.whk.scene.event.SceneMessage;
import com.whk.threadpool.handler.HandlerFactory;
import com.whk.threadpool.processor.ProcessorId;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 处理网关收到的消息
 */
@Service
@Slf4j
public class SceneKafkaMessageService extends KafkaMessageService {


    private DispatchProtocolService dispatchProtocolService;

    @Override
    public void init() {
        dispatchProtocolService = new DispatchProtocolService();
    }

    @Override
    @KafkaListener(topics = {"${game.kafka-topic.message-topic}-${game.data.zone}-${game.data.server}"}, groupId = "${game.kafka-topic.group-id}")
    public void consume(ConsumerRecord<String, byte[]> record) {
        var message = MessageInnerCoder.INSTANCE.readGameMessagePackage(record.value());
        message.ifPresent(msg -> {
            log.info("接受信息:" + msg.getCommand());
            try {
                var body = CmdToMessageUtil.getInstance().parsePayload(msg);
                dispatchProtocolService.dealMessage(msg.getCommand(),
                        method -> {
                            if (method.processorId().equals(ProcessorId.MAP_PROCESSOR)) {
                                var player = PlayerActorMgr.INSTANCE.getPlayer(msg.getPlayerId());
                                if (Objects.isNull(player)) return null;
                                return new SceneMessage(player.getMovement().getScene().getSceneId(), body, msg.getPlayerId(), method);
                            }
                            return HandlerFactory.INSTANCE.createPlayerHandler(body, msg.getPlayerId(), method);
                        });
            } catch (Exception e) {
                log.error("解析错误", e);
            }
        });
    }
}
