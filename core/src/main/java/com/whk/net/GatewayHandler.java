package com.whk.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.logging.Logger;

class GatewayHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(GatewayHandler.class.getName());

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
        logger.warning("gate way channel inactive !!! start reconnecting to " + remoteAddr + "......");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        setConnected(true);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ResponseTest result = new ResponseTest();
        RequestTest request = (RequestTest)msg;
        System.out.println(request.getCommand() + "," + request.getCommand() + "," + request.getOp());
        result.setCommand(request.getCommand());
        result.setUser(request.getUser());
        result.setRe("gotta something");
        ctx.writeAndFlush(result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        isConnected = false;
        logger.warning("RPC client disconnected " + ctx.channel() + ",cause => " + cause);
        ctx.close();
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

}
