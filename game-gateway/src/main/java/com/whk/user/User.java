package com.whk.user;

import com.whk.MessageI18n;
import com.whk.net.channel.ChannelChangeState;
import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.KafkaMessageService;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelPromise;
import lombok.Getter;
import lombok.Setter;
import com.whk.TipsConvert;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.MessageWrapperProto;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Getter
@Setter
public class User implements ChannelChangeState {
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
        return serverInfo.getPresentServer().getId();
    }


    public void sendToClientMessage(MessageProto.Message.Builder msg) {
        ctx.writeAndFlush(msg);
    }

    public void sendTips(int tipsId){
        MessageProto.Message.Builder builder = MessageProto.Message.newBuilder()
                .setCommand(0x0005).setTips(TipsConvert.convert(MessageI18n.getMessageTuple(tipsId)));
        sendToClientMessage(builder);
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
