package com.whk.net.channel;

import com.whk.net.enity.MapBeanServer;
import com.whk.net.enity.Message;
import io.netty.util.concurrent.Promise;

public interface GameChannelOutboundHandler extends GameChannelHandler {
    void writeAndFlush(AbstractGameChannelHandlerContext ctx, Message msg, GameChannelPromise promise) throws Exception;

    void close(AbstractGameChannelHandlerContext ctx, GameChannelPromise promise);
}
