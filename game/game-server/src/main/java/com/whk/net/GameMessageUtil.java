package com.whk.net;

import com.whk.actor.PlayerMgr;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.net.kafka.SendMessageHolder;
import com.whk.protobuf.message.MessageProto;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class GameMessageUtil extends SendMessageHolder {

    private GameMessageUtil() {
    }

    private static final GameMessageUtil INSTANCE = new GameMessageUtil();

    public static GameMessageUtil getInstance() {
        return INSTANCE;
    }

    @Override
    protected void sendMessage0(MessageProto.Message.Builder message, long playerId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) return;
        message.setPlayerId(playerId);
        MessageInnerCoder.INSTANCE.sendMessage(getKafkaMessageConsumeService(), message.build(), player.getServerInfo().getGateTopic());
    }
}
