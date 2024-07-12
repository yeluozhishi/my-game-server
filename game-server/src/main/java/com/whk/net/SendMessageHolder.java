package com.whk.net;

import com.whk.MessageI18n;
import com.whk.actor.PlayerMgr;
import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.TipsConvert;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.MessageWrapperProto;

import java.io.IOException;

public enum SendMessageHolder {
    INSTANCE;

    private KafkaMessageService kafkaMessageService;

    SendMessageHolder() {
    }

    public void init(KafkaMessageService kafkaMessageService){
        this.kafkaMessageService = kafkaMessageService;
    }

    public void sendMessage(MessageProto.Message message, long playerId) {
        PlayerMgr.INSTANCE.getPlayer(playerId).ifPresent(player -> {
            var messageWrapper = MessageWrapperProto.MessageWrapper.newBuilder()
                    .setMessage(message).setPlayerId(playerId)
                    .build();
            try {
                GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaMessageService, messageWrapper, player.getServerInfo().getGateTopic());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendTips(long playerId, int tipsId){
        MessageProto.Message.Builder builder = MessageProto.Message.newBuilder()
                .setCommand(0x0005).setTips(TipsConvert.convert(MessageI18n.getMessageTuple(tipsId)));
        sendMessage(builder.build(), playerId);
    }
}
