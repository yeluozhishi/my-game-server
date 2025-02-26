package com.whk.net;

import com.google.protobuf.ByteString;
import com.whk.CmdToMessageUtil;
import com.whk.MessageI18n;
import com.whk.actor.PlayerMgr;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.TipsConvert;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.TipsProto;

import java.io.IOException;

public enum SendMessageHolder {
    INSTANCE;

    private KafkaMessageService kafkaMessageService;

    SendMessageHolder() {
    }

    public void init(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    private void sendMessage(MessageProto.Message.Builder message, long playerId) {
        PlayerMgr.INSTANCE.getPlayer(playerId).ifPresent(player -> {
            message.setPlayerId(playerId);
            try {
                MessageInnerCoder.INSTANCE.sendMessage(kafkaMessageService, message.build(), player.getServerInfo().getGateTopic());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendMessage(Class<?> c, ByteString byteString, long playerId) {
        MessageProto.Message.Builder msg = MessageProto.Message.newBuilder();
        msg.setCommand(CmdToMessageUtil.getInstance().getCmd(c));
        msg.setPayload(byteString);
        sendMessage(msg, playerId);
    }

    public void sendTips(int tipsId, long playerId) {
        sendMessage(TipsProto.Tips.class, TipsConvert.convert(MessageI18n.getMessageTuple(tipsId)), playerId);
    }

    public void sendTips(int tipsId, long playerId, String... args) {
        sendMessage(TipsProto.Tips.class, TipsConvert.convert(MessageI18n.getMessageTuple(tipsId, args)), playerId);
    }
}
