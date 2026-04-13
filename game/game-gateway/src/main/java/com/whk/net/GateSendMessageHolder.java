package com.whk.net;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.whk.CmdToMessageUtil;
import com.whk.MessageWrap;
import com.whk.TipsConvert;
import com.whk.message.MESSAGE_CODE;
import com.whk.message.MapBean;
import com.whk.message.MessageI18n;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.net.kafka.SendMessageHolder;
import com.whk.protobuf.message.MessageProto;
import com.whk.user.User;
import com.whk.user.UserMgr;

public class GateSendMessageHolder extends SendMessageHolder {
    private static final GateSendMessageHolder INSTANCE = new GateSendMessageHolder();

    private GateSendMessageHolder() {
    }

    public static GateSendMessageHolder getInstance() {
        return INSTANCE;
    }

    @Override
    protected void sendMessage0(MessageProto.Message.Builder message, long userId) {

    }

    public void sendMessage(MessageWrap messageWrap, User user, String serverTopic) {
        MessageProto.Message.Builder msg = MessageProto.Message.newBuilder();
        msg.setCommand(messageWrap.cmd());
        msg.setPayload(messageWrap.body());
        msg.setPlayerId(user.getServerInfo().getPlayerId());
        MessageInnerCoder.INSTANCE.sendMessage(getKafkaMessageConsumeService(), msg.build(), serverTopic);
    }

    public void sendToClientMessage(MessageProto.Message message) throws InvalidProtocolBufferException {
        var body = CmdToMessageUtil.getInstance().parsePayload(message);
        UserMgr.INSTANCE.getUserByPlayerId(message.getPlayerId()).getSession().getHandler().writeAndFlush(body);
    }

    public void sendToClientMessage(Message message, long userId) {
        UserMgr.INSTANCE.getUserByUserId(userId).getSession().getHandler().writeAndFlush(message);
    }

    @Override
    public void sendTips(MESSAGE_CODE code, long userId) {
        sendToClientMessage(TipsConvert.convert(code.getCode(), MessageI18n.getMessage(code)), userId);
    }

    @Override
    public void sendTips(MESSAGE_CODE code, long userId, String... args) {
        sendToClientMessage(TipsConvert.convert(code.getCode(), MessageI18n.getMessage(code, args)), userId);
    }


    public void sendTips(MapBean tipsMsg, long userId) {
        sendToClientMessage(TipsConvert.convert(tipsMsg.getInt(MapBean.CODE_TAG), tipsMsg.getString(MapBean.MSG_TAG)), userId);
    }

}
