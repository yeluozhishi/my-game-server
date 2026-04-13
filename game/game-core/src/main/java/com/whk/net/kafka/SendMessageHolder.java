package com.whk.net.kafka;

import com.google.protobuf.Message;
import com.whk.CmdToMessageUtil;
import com.whk.MessageWrap;
import com.whk.TipsConvert;
import com.whk.message.MESSAGE_CODE;
import com.whk.message.MessageI18n;
import com.whk.protobuf.message.MessageProto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class SendMessageHolder {

    private KafkaMessageConsumeService kafkaMessageConsumeService;

    protected abstract void sendMessage0(MessageProto.Message.Builder message, long playerId);

    public void sendMessage(Message message, long playerId) {
        MessageProto.Message.Builder msg = MessageProto.Message.newBuilder();
        msg.setCommand(CmdToMessageUtil.getInstance().getCmd(message.getClass()));
        msg.setPayload(message.toByteString());
        msg.setPlayerId(playerId);
        sendMessage0(msg, playerId);
    }

    public void sendTips(MESSAGE_CODE code, long playerId) {
        sendMessage(TipsConvert.convert(code.getCode(), MessageI18n.getMessage(code)), playerId);
    }

    public void sendTips(MESSAGE_CODE code, long playerId, String... args) {
        sendMessage(TipsConvert.convert(code.getCode(), MessageI18n.getMessage(code, args)), playerId);
    }
}