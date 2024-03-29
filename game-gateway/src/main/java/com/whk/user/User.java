package com.whk.user;

import com.whk.net.channel.ChannelChangeState;
import com.whk.net.channel.GameChannel;
import com.whk.net.enity.Message;
import io.netty.channel.ChannelHandlerContext;

public class User implements ChannelChangeState {
    private String userId;

    private String playerId;

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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
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
