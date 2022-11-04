package com.whk.user;

import io.netty.channel.Channel;

public class User {
    private String userId;
    private int serverId;

    private String playerId;
    private Channel channel;

    public User() {
    }

    public User(String userId, int serverId, Channel channel) {
        this.userId = userId;
        this.channel = channel;
        this.serverId = serverId;
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

    public void sendToClientMessage(Object msg){
        channel.writeAndFlush(msg);
    }

}
