package com.whk.net.channel;

import com.whk.net.enity.Message;

public interface GameChannelOutboundHandler extends GameChannelHandler {
    void writeAndFlush(AbstractGameChannelHandlerContext ctx, Message msg, GameChannelPromise promise) throws Exception;

    void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise);
}
