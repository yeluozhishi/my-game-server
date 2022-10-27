package com.whk.user;

import io.netty.channel.Channel;

public class User {
    private String userId;
    private int serverId;
    private int toServerId;
    private Channel channel;

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

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getToServerId() {
        return toServerId;
    }

    public void setToServerId(int toServerId) {
        this.toServerId = toServerId;
    }

    public void sendToClientMessage(Object msg){
        channel.writeAndFlush(msg);
    }

}
