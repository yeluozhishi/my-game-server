package com.whk.net.channel;

import org.whk.protobuf.message.MessageOuterClass;
import org.whk.protobuf.message.MessageWrapperOuterClass;

public interface GameChannelInboundHandler extends GameChannelHandler{
    /**
     * GameChannel第一次注册的时候调用
     * @param ctx
     * @param playerId
     * @param promise
     */
    void channelRegister(AbstractGameChannelHandlerContext ctx, Long playerId, GameChannelPromise promise);

    /**
     * GameChannel被移除的时候调用
     * @param ctx
     * @throws Exception
     */
    void channelInactive(AbstractGameChannelHandlerContext ctx) throws Exception;

    /**
     * 读取并处理客户端发送的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    void channelRead(AbstractGameChannelHandlerContext ctx, MessageWrapperOuterClass.MessageWrapper msg) throws Exception;

}