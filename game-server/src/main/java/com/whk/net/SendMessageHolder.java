package com.whk.net;

import com.whk.actor.PlayerMgr;
import com.whk.net.enity.Message;
import com.whk.net.kafka.GameMessageInnerDecoder;
import org.springframework.kafka.core.KafkaTemplate;

public enum SendMessageHolder {
    INSTANCE;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    SendMessageHolder() {
    }

    public void init(KafkaTemplate<String, byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Message message, String playerId){
        PlayerMgr.INSTANCE.getPlayer(playerId).ifPresent(player ->
                GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, message, player.getGateInstanceId()));
    }



}
