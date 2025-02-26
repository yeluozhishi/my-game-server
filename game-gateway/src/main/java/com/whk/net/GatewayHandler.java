package com.whk.net;


import com.whk.SpringUtils;
import com.whk.protobuf.message.MessageProto;
import com.whk.service.TransmitAndDispatch;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端连接网关 消息处理
 */
@Slf4j
public class GatewayHandler extends ChannelInboundHandlerAdapter {

    private TransmitAndDispatch transmitAndDispatch;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info(ctx.channel().remoteAddress() + " is channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("client channel inactive !!! " + ctx.channel().attr(UserMgr.INSTANCE.ATTR_USER_ID).toString() + ":" + ctx.channel().remoteAddress() + " ......");
        super.channelInactive(ctx);
        long userId = Long.parseLong(ctx.channel().attr(UserMgr.INSTANCE.ATTR_USER_ID).get().toString());
        UserMgr.INSTANCE.logOut(userId);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageProto.Message message = (MessageProto.Message) msg;
        // 根据command分发给对应的方法，由方法获取对应的body。
        if (transmitAndDispatch == null){
            transmitAndDispatch = SpringUtils.getBean(TransmitAndDispatch.class);
        }
        transmitAndDispatch.consumerClientMessage(message, ctx);
    }

}
