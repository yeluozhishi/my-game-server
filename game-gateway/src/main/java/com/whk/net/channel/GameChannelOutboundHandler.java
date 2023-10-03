package com.whk.net.channel;


import org.whk.protobuf.message.MessageOuterClass;
import org.whk.protobuf.message.MessageWrapperOuterClass;

public interface GameChannelOutboundHandler extends GameChannelHandler {
    void writeAndFlush(AbstractGameChannelHandlerContext ctx, MessageWrapperOuterClass.MessageWrapper msg, GameChannelPromise promise) throws Exception;

    void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise);
}
