package com.whk.rpc.consumer;

import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;
import com.whk.rpc.serialize.RpcSerializeProtocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MessageSendHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = Logger.getLogger(MessageSendHandler.class.getName());

    private final Map<String, MessageCallBack> mapCallBack = new HashMap<>();
    private volatile Channel channel;
    private SocketAddress remoteAddr;
    private Boolean isConnected = false;
    private EventLoopGroup eventLoopGroup;
    private RpcSerializeProtocol serializeProtocol;


    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddr() {
        return remoteAddr;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remoteAddr = this.channel.remoteAddress();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        isConnected = false;
        logger.warning("NettyRPC channel inactive !!! start reconnecting to " + remoteAddr + "......");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageResponse response = (MessageResponse) msg;
//        String messageId = response.getMessageId();
//        MessageCallBack callBack = mapCallBack.get(messageId);
//        if (callBack != null) {
//            mapCallBack.remove(messageId);
//            callBack.over(response);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        isConnected = false;
        logger.warning("RPC client disconnected " + ctx.channel() + ",cause => " + cause);
        ctx.close();
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        eventLoopGroup.shutdownGracefully();
    }

    public MessageCallBack sendRequest(MessageRequest request) {
        MessageCallBack callBack = new MessageCallBack(request);
        mapCallBack.put(request.getMessageId(), callBack);
        channel.writeAndFlush(request);
        return callBack;
    }

    public Object sendRequestWithoutCallback(MessageRequest request) {
            logger.info("RPC Client -> Server:" + request.getMessageId() + ", " +
                    "debugInfo:" + request);
        channel.writeAndFlush(request);
        return null;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    public void setEventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
    }

    public RpcSerializeProtocol getSerializeProtocol() {
        return serializeProtocol;
    }

    public void setSerializeProtocol(RpcSerializeProtocol serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }
}
