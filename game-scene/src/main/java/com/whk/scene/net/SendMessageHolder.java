package com.whk.scene.net;

import com.whk.MessageI18n;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.TipsConvert;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.MessageWrapperProto;
import com.whk.scene.actor.PlayerActorMgr;

import java.io.IOException;

public enum SendMessageHolder {
    INSTANCE;

    private KafkaMessageService kafkaMessageService;

    SendMessageHolder() {
    }

    public void init(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    public void sendMessage(MessageProto.Message message, long playerId) {
        PlayerActorMgr.INSTANCE.getPlayer(playerId).ifPresent(player -> {
            var messageWrapper = MessageWrapperProto.MessageWrapper.newBuilder()
                    .setMessage(message).setPlayerId(playerId)
                    .build();
            try {
                MessageInnerCoder.INSTANCE.sendMessage(kafkaMessageService, messageWrapper, player.getGateTopic());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendTips(int tipsId, long playerId) {
        sendMessage(TipsConvert.convert(MessageI18n.getMessageTuple(tipsId)).build(), playerId);
    }

    public void sendTips(int tipsId, long playerId, String... args) {
        sendMessage(TipsConvert.convert(MessageI18n.getMessageTuple(tipsId, args)).build(), playerId);
    }
}
