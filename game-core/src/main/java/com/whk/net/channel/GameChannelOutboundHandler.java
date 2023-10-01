package com.whk.net.channel;


import org.whk.protobuf.message.MessageOuterClass;

public interface GameChannelOutboundHandler extends GameChannelHandler {
    void writeAndFlush(AbstractGameChannelHandlerContext ctx, MessageOuterClass.Message msg, GameChannelPromise promise) throws Exception;

    void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise);
}
