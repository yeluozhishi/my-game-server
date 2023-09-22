package com.whk.net;

import com.whk.actor.PlayerMgr;
import com.whk.net.enity.Message;
import com.whk.net.kafka.GameMessageInnerDecoder;
import org.springframework.kafka.core.KafkaTemplate;

public enum SendMessageHolder {
    INSTANCE;

    private KafkaTemplate<Long, byte[]> kafkaTemplate;

    SendMessageHolder() {
    }

    public void init(KafkaTemplate<Long, byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Message message){
        PlayerMgr.INSTANCE.getPlayer(message.getPlayerId()).ifPresent(player ->
                GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, message, player.getGateInstanceId()));
    }



}
