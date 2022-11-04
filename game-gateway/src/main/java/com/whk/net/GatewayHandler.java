package com.whk.net;

import com.whk.service.ServerConnector;
import com.whk.user.User;
import com.whk.user.UserMgr;
import com.whk.util.Auth0JwtUtils;
import com.whk.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.SocketAddress;
import java.util.logging.Logger;

/**
 * 客户端连接网关 消息处理
 */
public class GatewayHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = Logger.getLogger(GatewayHandler.class.getName());


    private ServerConnector serverConnector;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info(ctx.channel().remoteAddress() + " is channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.warning("gate way channel inactive !!! start reconnecting to " + ctx.channel().remoteAddress() + " ......");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message)msg;
        UserMgr.INSTANCE.userLogin(message, ctx.channel());
        if (serverConnector == null){
            serverConnector = SpringUtil.getAppContext().getBean(ServerConnector.class);
        }
        serverConnector.sendMessage(message);
    }

}
