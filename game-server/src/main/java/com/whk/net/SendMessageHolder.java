package com.whk.net;

import com.whk.actor.PlayerMgr;
import com.whk.net.kafka.GameMessageInnerDecoder;
import org.springframework.kafka.core.KafkaTemplate;
import org.whk.protobuf.message.MessageOuterClass;

import java.io.IOException;

public enum SendMessageHolder {
    INSTANCE;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    SendMessageHolder() {
    }

    public void init(KafkaTemplate<String, byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageOuterClass.Message message) throws IOException {
        var player = PlayerMgr.INSTANCE.getPlayer(message.getPlayerId());
        if (player.isPresent()){
            message.toBuilder().setServerInstance(player.get().getGateInstanceId());
            GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, message);
        }

    }



}
