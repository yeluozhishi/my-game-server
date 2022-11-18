package com.whk.user;

import com.whk.net.channel.ChannelChangeState;
import com.whk.net.channel.GameChannel;
import io.netty.channel.Channel;

public class User implements ChannelChangeState {
    private String userId;
    private int serverId;

    private int toServerId;

    private String playerId;
    private Channel channel;

    private boolean completed = false;

    private GameChannel gameChannel;

    public User(String userId, int serverId, int toServerId, Channel channel, GameChannel gameChannel) {
        this.userId = userId;
        this.serverId = serverId;
        this.toServerId = toServerId;
        this.channel = channel;
        this.gameChannel = gameChannel;
    }

    public String getUserId() {
        return userId;
    }

    public int getServerId() {
        return serverId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void sendToClientMessage(Object msg){
        channel.writeAndFlush(msg);
    }

    public int getToServerId() {
        return toServerId;
    }

    public void setToServerId(int toServerId) {
        this.toServerId = toServerId;
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

    @Override
    public void fireChannelInactive() {
        // 移除user
        UserMgr.INSTANCE.removeUser(userId);
        // 关闭channel
        channel.closeFuture();
    }
}
