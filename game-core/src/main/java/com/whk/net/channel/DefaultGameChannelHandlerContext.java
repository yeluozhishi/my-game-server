package com.whk.net.channel;

import io.netty.util.concurrent.EventExecutor;

public class DefaultGameChannelHandlerContext extends AbstractGameChannelHandlerContext{
    private final GameChannelHandler handler;

    public DefaultGameChannelHandlerContext(GameChannelPipeline pipeline, EventExecutor executor, String name, GameChannelHandler handler) {
        super(isInbound(handler), isOutbound(handler), pipeline, executor, name);
        this.handler = handler;
    }

    @Override
    public GameChannelHandler handler() {
        return handler;
    }

    private static boolean isInbound(GameChannelHandler handler) {
        return handler instanceof GameChannelInboundHandler;
    }

    private static boolean isOutbound(GameChannelHandler handler) {
        return handler instanceof GameChannelOutboundHandler;
    }
}
