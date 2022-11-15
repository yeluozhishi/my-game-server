package com.whk.user;

import com.whk.net.channel.GameChannel;
import io.netty.channel.Channel;

public class User {
    private String userId;
    private int serverId;

    private int toServerId;

    private String playerId;
    private Channel channel;

    private GameChannel gameChannel;

    public User() {
    }

    public User(String userId, int serverId, int toServerId, Channel channel) {
        this.userId = userId;
        this.serverId = serverId;
        this.toServerId = toServerId;
        this.channel = channel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setGameChannel(GameChannel gameChannel) {
        this.gameChannel = gameChannel;
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
}
