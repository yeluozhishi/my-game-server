package com.whk.net;

import com.whk.net.channel.AbstractGameChannelHandlerContext;
import com.whk.net.channel.GameChannelInboundHandler;
import com.whk.net.channel.GameChannelOutboundHandler;
import com.whk.net.channel.GameChannelPromise;
import com.whk.net.enity.Message;

public class MessageHandler implements GameChannelInboundHandler, GameChannelOutboundHandler {
    @Override
    public void exceptionCaught(AbstractGameChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    @Override
    public void channelRegister(AbstractGameChannelHandlerContext ctx, String playerId, GameChannelPromise promise) {

    }

    @Override
    public void channelInactive(AbstractGameChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(AbstractGameChannelHandlerContext ctx, Message msg) throws Exception {
        ctx.gameChannel().sendToServerMessage(msg);
    }

    @Override
    public void writeAndFlush(AbstractGameChannelHandlerContext ctx, Message msg, GameChannelPromise promise) throws Exception {

    }

    @Override
    public void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise) {

    }
}
