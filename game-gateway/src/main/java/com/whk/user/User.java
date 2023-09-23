package com.whk.user;

import com.whk.net.channel.ChannelChangeState;
import com.whk.net.channel.GameChannel;
import io.netty.channel.ChannelHandlerContext;
import org.whk.message.Message;

public class User implements ChannelChangeState {
    private String userId;

    private Long playerId;

    private ChannelHandlerContext ctx;

    private boolean completed = false;

    private GameChannel gameChannel;

    public User(String userId, ChannelHandlerContext ctx, GameChannel gameChannel) {
        this.userId = userId;
        this.ctx = ctx;
        this.gameChannel = gameChannel;
    }

    public String getUserId() {
        return userId;
    }

    public int getServerId() {
        return gameChannel.getServerId();
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void sendToClientMessage(Message msg){
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

    public void sendToServerMessage(Message message){
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
