package com.whk.client.net;

import com.whk.threadpool.dispatchprotocol.DispatchProtocolService;
import com.whk.threadpool.event.EventFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.whk.protobuf.message.MessageProto;

import java.net.SocketAddress;
import java.util.logging.Logger;


public class Gamehandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = Logger.getLogger(Gamehandler.class.getName());

    private volatile Channel channel;
    private SocketAddress remoteAddr;

    private Boolean isConnected = false;

    private DispatchProtocolService dispatchProtocolService;

    public Gamehandler(DispatchProtocolService dispatchProtocolService) {
        this.dispatchProtocolService = dispatchProtocolService;
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
        logger.warning("channel inactive !!! start reconnecting to " + remoteAddr + "......");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        setConnected(true);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageProto.Message result = (MessageProto.Message) msg;
        System.out.printf("game channel read:%s%n", result);
        try {
            dispatchProtocolService.dealMessage(result, 0L,
                    method -> EventFactory.INSTANCE.createPlayerEvent(result, 0L, method));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        isConnected = false;
        logger.warning("client disconnected " + ctx.channel() + ",cause => " + cause);
        ctx.close();
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

}
