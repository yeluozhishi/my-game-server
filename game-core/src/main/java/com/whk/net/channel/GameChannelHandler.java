package com.whk.net.channel;

public interface GameChannelHandler {

    /**
     * 错误
     * @param ctx 处理
     * @param cause 异常
     * @throws Exception
     */
    void exceptionCaught(AbstractGameChannelHandlerContext ctx, Throwable cause) throws Exception;
}
