package com.whk.net;


import com.whk.CmdToMessageUtil;
import com.whk.SpringUtils;
import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.protobuf.message.MessageProto;
import com.whk.threadpool.handler.HandlerFactory;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 客户端连接网关 消息处理
 */
@Slf4j
public class GatewayHandler extends ChannelInboundHandlerAdapter {

    private DispatchProtocolService dispatchProtocolService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("%s is channelActive".formatted(ctx.channel().remoteAddress()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("client channel inactive !!! %s ......".formatted(ctx.channel().attr(UserMgr.INSTANCE.SESSION).toString()));
        super.channelInactive(ctx);
        if (ctx.channel().hasAttr(UserMgr.INSTANCE.SESSION)) {
            UserMgr.INSTANCE.logOut(ctx.channel().attr(UserMgr.INSTANCE.SESSION).get().getId());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageProto.Message message = (MessageProto.Message) msg;
        // 根据command分发给对应的方法，由方法获取对应的body。
        if (dispatchProtocolService == null) {
            dispatchProtocolService = SpringUtils.getBean(DispatchProtocolService.class);
        }
        consumerClientMessage(message, ctx);
    }


    /**
     * 消费客户端消息
     */
    public void consumerClientMessage(MessageProto.Message message, ChannelHandlerContext ctx) {
        Session session = ctx.channel().attr(UserMgr.INSTANCE.SESSION).get();
        if (Objects.isNull(session)) {
            ctx.close();
            return;
        }
        try {
            var body = CmdToMessageUtil.getInstance().parsePayload(message);
            if (dispatchProtocolService.getMethods().containsKey(message.getCommand())) {
                dispatchProtocolService.dealMessage(message.getCommand(),
                        method -> HandlerFactory.INSTANCE.createUserHandler(body, session.getId(), method));
            } else {
                if (message.getPlayerId() == 0L) {
                    return;
                }
                UserMgr.INSTANCE.sendToServerMessage(UserMgr.INSTANCE.wrapperMessage(message, session.getId()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
