package com.whk.client.net;

import com.google.protobuf.Message;
import com.whk.MessageWrap;
import com.whk.client.model.User;
import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.threadpool.handler.HandlerFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.Arrays;

@Slf4j
public class Gamehandler extends ChannelInboundHandlerAdapter {

    private volatile Channel channel;
    private SocketAddress remoteAddr;

    private Boolean isConnected = false;

    private final DispatchProtocolService dispatchProtocolService;

    private User user;

    public Gamehandler(DispatchProtocolService dispatchProtocolService, User user) {
        this.dispatchProtocolService = dispatchProtocolService;
        this.user = user;
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
        log.warn("channel inactive !!! start reconnecting to " + remoteAddr + "......");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        setConnected(true);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageWrap message = (MessageWrap) msg;
        try {
            Message body = message.decode();
            dispatchProtocolService.dealMessage(message.cmd(),
                    method -> HandlerFactory.INSTANCE.createPlayerHandler(body, user.getUserId(), method));
        } catch (Exception e) {
            log.error(e.getMessage() + "; " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        isConnected = false;
        log.warn("client disconnected " + ctx.channel() + ",cause => " + cause);
        ctx.close();
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

}
