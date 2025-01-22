package com.whk.user;

import com.whk.MessageI18n;
import com.whk.TipsConvert;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.MessageWrapperProto;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

@Getter
@Setter
public class User {
    Logger logger = Logger.getLogger(User.class.getName());

    private Long userId;

    private final ChannelHandlerContext ctx;

    private PlayerServerInfo serverInfo;

    private KafkaMessageService kafkaMessageService;

    private boolean passPort;


    public User(Long userId, ChannelHandlerContext ctx, PlayerServerInfo serverInfo) {
        this.userId = userId;
        this.ctx = ctx;
        this.serverInfo = serverInfo;
    }


    public int getServerId() {
        return serverInfo.getServer().getId();
    }

    public int getSceneServerId() {
        if (Objects.isNull(serverInfo.getSceneServer())) return 0;
        return serverInfo.getSceneServer().getId();
    }


    public void sendToClientMessage(MessageProto.Message.Builder msg) {
        ctx.writeAndFlush(msg);
    }

    public void sendTips(int tipsId){
        sendToClientMessage(TipsConvert.convert(MessageI18n.getMessageTuple(tipsId)));
    }

    public void sendTips(int tipsId, String... args){
        sendToClientMessage(TipsConvert.convert(MessageI18n.getMessageTuple(tipsId, args)));
    }

    public void sendToServerMessage(MessageWrapperProto.MessageWrapper message) throws IOException {
        MessageInnerCoder.INSTANCE.sendMessage(kafkaMessageService, message, getServerInfo().getSceneServerTopic());
    }
}
