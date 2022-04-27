package com.whk.net.game;

import com.whk.net.ResponseTest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.logging.Logger;

public class Gamehandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(Gamehandler.class.getName());

    private volatile Channel channel;
    private SocketAddress remoteAddr;
    private Boolean isConnected = false;


    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddr() {
        return remoteAddr;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remoteAddr = this.channel.remoteAddress();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        isConnected = false;
        logger.warning("channel inactive !!! start reconnecting to " + remoteAddr + "......");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        setConnected(true);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ResponseTest result = (ResponseTest)msg;
        System.out.println(result.getCommand() + "," + result.getUser() + "," + result.getRe());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        isConnected = false;
        logger.warning("client disconnected " + ctx.channel() + ",cause => " + cause);
        ctx.close();
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

}
