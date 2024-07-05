package com.whk.net.channel;

import com.whk.protobuf.message.MessageWrapperProto;

public class MessageHandler implements GameChannelInboundHandler, GameChannelOutboundHandler {
    @Override
    public void exceptionCaught(AbstractGameChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    @Override
    public void channelRegister(AbstractGameChannelHandlerContext ctx, Long playerId, GameChannelPromise promise) {
        promise.setSuccess();
        System.out.println("注册结束");
    }

    @Override
    public void channelInactive(AbstractGameChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(AbstractGameChannelHandlerContext ctx, MessageWrapperProto.MessageWrapper msg) throws Exception {
        ctx.gameChannel().sendToServerMessage(msg);
    }

    @Override
    public void writeAndFlush(AbstractGameChannelHandlerContext ctx, MessageWrapperProto.MessageWrapper msg, GameChannelPromise promise) throws Exception {

    }

    @Override
    public void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise) {

    }
}
