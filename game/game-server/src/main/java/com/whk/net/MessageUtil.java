package com.whk.net;

import com.whk.actor.PlayerMgr;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.net.kafka.SendMessageHolder;
import com.whk.protobuf.message.MessageProto;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class MessageUtil extends SendMessageHolder {

    private MessageUtil() {
    }

    public static MessageUtil getInstance() {
        return Holder.util;
    }

    @Override
    protected void sendMessage0(MessageProto.Message.Builder message, long playerId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) return;
        message.setPlayerId(playerId);
        MessageInnerCoder.INSTANCE.sendMessage(getKafkaMessageConsumeService(), message.build(), player.getServerInfo().getGateTopic());
    }

    private static class Holder {
        private static final MessageUtil util = new MessageUtil();
    }
}
