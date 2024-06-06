package com.whk.user;

import com.whk.net.channel.ChannelChangeState;
import com.whk.net.channel.GameChannel;
import io.netty.channel.ChannelHandlerContext;
import org.whk.protobuf.message.MessageProto;
import org.whk.protobuf.message.MessageWrapperProto;

public class User implements ChannelChangeState {
    private Long userId;

    private Long playerId;

    private final ChannelHandlerContext ctx;

    private boolean completed = false;

    private GameChannel gameChannel;

    public User(Long userId, ChannelHandlerContext ctx, GameChannel gameChannel) {
        this.userId = userId;
        this.ctx = ctx;
        this.gameChannel = gameChannel;
    }

    public Long getUserId() {
        return userId;
    }

    public int getServerId() {
        return gameChannel.getServerId();
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void sendToClientMessage(MessageProto.Message msg){
        ctx.writeAndFlush(msg);
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public void completed() {
        gameChannel.register(playerId, this);
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void sendToServerMessage(MessageWrapperProto.MessageWrapper message){
        gameChannel.fireReadGameMessage(message);
    }

    @Override
    public void fireChannelInactive() {
        // 移除user
        UserMgr.INSTANCE.removeUser(userId);
        // 关闭channel
        ctx.close();
    }
}
