package com.whk.net;

import com.whk.net.protobuf.Message;
import com.whk.service.ServerConnector;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.whk.SpringUtils;

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
        logger.warning("gate way channel inactive !!! " + ctx.channel().<String>attr(UserMgr.INSTANCE.ATTR_USERNAME).get().toString() + ":" + ctx.channel().remoteAddress() + " ......");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Message message = (Message)msg;
//        if (serverConnector == null){
//            serverConnector = SpringUtils.getBean(ServerConnector.class);
//        }
//        serverConnector.sendMessage(message, ctx);
        System.out.println(message);
    }

}
