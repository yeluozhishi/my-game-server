package com.whk.user;

import com.whk.MessageI18n;
import com.whk.net.channel.ChannelChangeState;
import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.KafkaMessageService;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import org.whk.TipsConvert;
import org.whk.protobuf.message.MessageProto;
import org.whk.protobuf.message.MessageWrapperProto;

import java.io.IOException;

@Getter
@Setter
public class User implements ChannelChangeState {
    private Long userId;

    private final ChannelHandlerContext ctx;

    private PlayerServerInfo serverInfo;

    private KafkaMessageService kafkaMessageService;

    private boolean passPort;

    public User(Long userId, ChannelHandlerContext ctx, PlayerServerInfo serverInfo, KafkaMessageService kafkaMessageService, boolean passPort) {
        this.userId = userId;
        this.ctx = ctx;
        this.serverInfo = serverInfo;
        this.kafkaMessageService = kafkaMessageService;
        this.passPort = passPort;
    }


    public int getServerId() {
        return serverInfo.getPresentServer().getServerId();
    }


    public void sendToClientMessage(MessageProto.Message msg) {
        ctx.writeAndFlush(msg);
    }

    public void sendTips(int tipsId){
        MessageProto.Message.Builder builder = MessageProto.Message.newBuilder()
                .setCommand(0x0005).setTips(TipsConvert.convert(MessageI18n.getMessageTuple(tipsId)));
        sendToClientMessage(builder.build());
    }


    public void sendToServerMessage(MessageWrapperProto.MessageWrapper message) throws IOException {
        GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaMessageService, message, getServerInfo().getPresentServer().getInstanceId());
    }

    @Override
    public void fireChannelInactive() {
        // 移除user
        UserMgr.INSTANCE.removeUser(userId);
        // 关闭channel
        ctx.close();
    }
}
