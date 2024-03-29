package com.whk.client.net;

import com.whk.net.dispatchprotocol.DispatchProtocolService;
import com.whk.net.enity.Message;
import com.whk.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketAddress;
import java.util.logging.Logger;


public class Gamehandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = Logger.getLogger(Gamehandler.class.getName());

    private volatile Channel channel;
    private SocketAddress remoteAddr;

    private Boolean isConnected = false;

    private DispatchProtocolService dispatchProtocolService;

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
        Message result = (Message) msg;
        System.out.println("game channel read:" + result.getCommand() + "," + result.getPlayerId() + "," + result.getBody());
        try {
            dispatchProtocolService.dealMessage(result);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
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

    public void init() {
        this.dispatchProtocolService = new DispatchProtocolService();
        dispatchProtocolService.setApplicationContext(SpringUtil.getAppContext());
        dispatchProtocolService.init();
    }
}
