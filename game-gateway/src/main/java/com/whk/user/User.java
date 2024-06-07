package com.whk.user;

import com.whk.net.channel.ChannelChangeState;
import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.net.kafka.KafkaMessageService;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;
import org.whk.protobuf.message.MessageProto;
import org.whk.protobuf.message.MessageWrapperProto;

import java.io.IOException;

@Getter
@Setter
public class User implements ChannelChangeState {
    private Long userId;

    private Long playerId;

    private final ChannelHandlerContext ctx;

    private PlayerServerInfo serverInfo;

    private KafkaMessageService kafkaMessageService;

    public User(Long userId, ChannelHandlerContext ctx, PlayerServerInfo serverInfo, KafkaMessageService kafkaMessageService) {
        this.userId = userId;
        this.ctx = ctx;
        this.serverInfo = serverInfo;
        this.kafkaMessageService = kafkaMessageService;
    }


    public int getServerId() {
        return serverInfo.getServerId();
    }


    public void sendToClientMessage(MessageProto.Message msg) {
        ctx.writeAndFlush(msg);
    }


    public void sendToServerMessage(MessageWrapperProto.MessageWrapper message) throws IOException {
        GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaMessageService, message);
    }

    @Override
    public void fireChannelInactive() {
        // 移除user
        UserMgr.INSTANCE.removeUser(userId);
        // 关闭channel
        ctx.close();
    }
}
