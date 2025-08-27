package com.whk.user;

import com.google.protobuf.ByteString;
import com.whk.CmdToMessageUtil;
import com.whk.message.MESSAGE_CODE;
import com.whk.message.MessageI18n;
import com.whk.TipsConvert;
import com.whk.net.Session;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.TipsProto;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Getter
@Setter
@Slf4j
public class User {

    private Long userId;

    private final Session session;

    private PlayerServerInfo serverInfo;

    public User(Long userId, ChannelHandlerContext ctx, PlayerServerInfo serverInfo) {
        this.userId = userId;
        this.serverInfo = serverInfo;
        this.session = new Session(ctx);
        session.setId(userId);
    }


    public int getServerId() {
        return serverInfo.getServer().getId();
    }


    public void sendToClientMessage(MessageProto.Message.Builder msg) {
        session.getHandler().writeAndFlush(msg.build());
    }

    public void sendToClientMessage(Class<?> c, ByteString byteString) {
        MessageProto.Message.Builder msg = MessageProto.Message.newBuilder();
        msg.setCommand(CmdToMessageUtil.getInstance().getCmd(c));
        msg.setPayload(byteString);
        sendToClientMessage(msg);
    }

    public void sendTips(MESSAGE_CODE code){
        sendToClientMessage(TipsProto.Tips.class, TipsConvert.convert(code.getCode(), MessageI18n.getMessage(code)));
    }

    public void sendTips(MESSAGE_CODE code, String... args){
        sendToClientMessage(TipsProto.Tips.class, TipsConvert.convert(code.getCode(), MessageI18n.getMessage(code, args)));
    }

    public void sendToServerMessage(MessageProto.Message.Builder message, KafkaMessageService service) throws IOException {
        MessageInnerCoder.INSTANCE.sendMessage(service, message.build(), getServerInfo().getSceneServerTopic(message.getCommand()));
    }
}
