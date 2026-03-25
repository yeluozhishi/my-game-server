package com.whk.scene.net;

import com.google.protobuf.ByteString;
import com.whk.CmdToMessageUtil;
import com.whk.message.MESSAGE_CODE;
import com.whk.message.MessageI18n;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.TipsConvert;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.TipsProto;
import com.whk.scene.actor.PlayerActorMgr;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public enum SendMessageHolder {
    INSTANCE;

    private KafkaMessageService kafkaMessageService;

    SendMessageHolder() {
    }

    public void init(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    private void sendMessage(MessageProto.Message.Builder message, long playerId) {
        var player = PlayerActorMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) return;
        message.setPlayerId(playerId);
        try {
            MessageInnerCoder.INSTANCE.sendMessage(kafkaMessageService, message.build(), player.getGateTopic());
        } catch (IOException e) {
            log.error("%s; %s".formatted(e.getMessage(), Arrays.toString(e.getStackTrace())));
        }
    }

    public void sendMessage(Class<?> c, ByteString byteString, long playerId) {
        MessageProto.Message.Builder msg = MessageProto.Message.newBuilder();
        msg.setCommand(CmdToMessageUtil.getInstance().getCmd(c));
        msg.setPayload(byteString);
        sendMessage(msg, playerId);
    }

    public void sendTips(MESSAGE_CODE code, long playerId) {
        sendMessage(TipsProto.Tips.class, TipsConvert.convert(code.getCode(), MessageI18n.getMessage(code)), playerId);
    }

    public void sendTips(MESSAGE_CODE code, long playerId, String... args) {
        sendMessage(TipsProto.Tips.class, TipsConvert.convert(code.getCode(), MessageI18n.getMessage(code, args)), playerId);
    }
}
