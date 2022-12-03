package com.whk.user;

import com.whk.net.channel.ChannelChangeState;
import com.whk.net.channel.GameChannel;
import com.whk.net.enity.Message;
import io.netty.channel.Channel;

public class User implements ChannelChangeState {
    private String userId;

    private String playerId;

    private Channel channel;

    private boolean completed = false;

    private GameChannel gameChannel;

    public User(String userId, Channel channel, GameChannel gameChannel) {
        this.userId = userId;
        this.channel = channel;
        this.gameChannel = gameChannel;
    }

    public String getUserId() {
        return userId;
    }

    public int getServerId() {
        return gameChannel.getServerId();
    }

    public Channel getChannel() {
        return channel;
    }

    public void sendToClientMessage(Object msg){
        channel.writeAndFlush(msg);
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
        channel.closeFuture();
    }
}
