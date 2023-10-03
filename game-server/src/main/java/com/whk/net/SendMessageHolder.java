package com.whk.net;

import com.whk.actor.PlayerMgr;
import com.whk.net.kafka.GameMessageInnerDecoder;
import org.springframework.kafka.core.KafkaTemplate;
import org.whk.protobuf.message.MessageOuterClass;
import org.whk.protobuf.message.MessageWrapperOuterClass;

import java.io.IOException;

public enum SendMessageHolder {
    INSTANCE;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    SendMessageHolder() {
    }

    public void init(KafkaTemplate<String, byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageOuterClass.Message message, Long playerId) throws IOException {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        if (player.isPresent()){
            var messageWrapper = MessageWrapperOuterClass.MessageWrapper.newBuilder()
                    .setMessage(message).setPlayerId(playerId).setServerInstance(player.get().getGateInstanceId())
                    .setUserId(player.get().getUserAccountId()).build();
            GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, messageWrapper);
        }
    }



}
