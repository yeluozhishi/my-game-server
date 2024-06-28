package com.whk.net;


import com.whk.service.GameGateConnector;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.whk.SpringUtils;
import org.whk.protobuf.message.MessageProto;

import java.util.logging.Logger;

/**
 * 客户端连接网关 消息处理
 */
public class GatewayHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = Logger.getLogger(GatewayHandler.class.getName());


    private GameGateConnector gameGateConnector;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info(ctx.channel().remoteAddress() + " is channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warning("gate way channel inactive !!! " + ctx.channel().<Long>attr(UserMgr.INSTANCE.ATTR_USER_ID).toString() + ":" + ctx.channel().remoteAddress() + " ......");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageProto.Message message = (MessageProto.Message) msg;
        // 根据command分发给对应的方法，由方法获取对应的body。
        if (gameGateConnector == null){
            gameGateConnector = SpringUtils.getBean(GameGateConnector.class);
        }
        gameGateConnector.consumerClientMessage(message, ctx);
    }

}
