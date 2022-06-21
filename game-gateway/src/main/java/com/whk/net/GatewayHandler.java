package com.whk.net;

import com.whk.service.ServerConnector;
import com.whk.user.User;
import com.whk.user.UserMgr;
import com.whk.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.logging.Logger;

public class GatewayHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = Logger.getLogger(GatewayHandler.class.getName());

    private volatile Channel channel;
    private SocketAddress remoteAddr;

    private ServerConnector serverConnector;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remoteAddr = this.channel.remoteAddress();
        logger.info(this.remoteAddr + "channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.warning("gate way channel inactive !!! start reconnecting to " + remoteAddr + "......");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
        setConnected();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message)msg;
        userLogin(message, ctx.channel());
        if (serverConnector == null){
            serverConnector = SpringUtil.getAppContext().getBean(ServerConnector.class);
        }

        serverConnector.sendMessage(message);
    }

    public void setConnected() {
    }

    private void userLogin(Message message, Channel channel){
        if (message.getCommand() == 0){
            User user = new User(message.getUserNames().get(0), message.getToServerId(), 1, channel);
            UserMgr.INSTANCE.addUser(user);
        }
    }


}
