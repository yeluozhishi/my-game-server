package com.whk.scene.net;

import com.whk.net.kafka.MessageInnerCoder;
import com.whk.net.kafka.SendMessageHolder;
import com.whk.protobuf.message.MessageProto;
import com.whk.scene.actor.PlayerActorMgr;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class SceneSendMessageHolder extends SendMessageHolder {

    private static final SceneSendMessageHolder INSTANCE = new SceneSendMessageHolder();

    private SceneSendMessageHolder() {
    }

    public static SceneSendMessageHolder getInstance() {
        return INSTANCE;
    }


    public void sendMessage0(MessageProto.Message.Builder message, long playerId) {
        var player = PlayerActorMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) return;
        message.setPlayerId(playerId);
        MessageInnerCoder.INSTANCE.sendMessage(getKafkaMessageConsumeService(), message.build(), player.getGateTopic());
    }

}
