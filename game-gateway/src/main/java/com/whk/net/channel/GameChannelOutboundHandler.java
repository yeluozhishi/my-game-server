package com.whk.net.channel;


import org.whk.protobuf.message.MessageWrapperProto;

public interface GameChannelOutboundHandler extends GameChannelHandler {
    void writeAndFlush(AbstractGameChannelHandlerContext ctx, MessageWrapperProto.MessageWrapper msg, GameChannelPromise promise) throws Exception;

    void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise);
}
