package com.whk.net;


import com.google.protobuf.Message;
import com.whk.MessageWrap;
import com.whk.Router;
import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.message.Server;
import com.whk.protobuf.message.MSGIDProto;
import com.whk.threadpool.handler.HandlerFactory;
import com.whk.user.User;
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

    private final DispatchProtocolService dispatchProtocolService;

    public GatewayHandler(DispatchProtocolService dispatchProtocolService) {
        this.dispatchProtocolService = dispatchProtocolService;
    }

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
        MessageWrap message = (MessageWrap) msg;
        consumerClientMessage(message, ctx);
    }


    /**
     * 消费客户端消息
     */
    public void consumerClientMessage(MessageWrap messageWrap, ChannelHandlerContext ctx) {
        Session session = ctx.channel().attr(UserMgr.INSTANCE.SESSION).get();
        if (Objects.isNull(session)) {
            ctx.close();
            return;
        }
        try {
            MSGIDProto.MSGID serverType = Router.getInstance().getServerType(messageWrap.cmd());
            User user = UserMgr.INSTANCE.getUserByUserId(session.getId());
            switch (serverType) {
                case game_server:
                    if (user.getServerInfo().getPlayerId() == 0L) {
                        return;
                    }
                    String gameTopic = getServerTopic(user.getServerInfo().getDataServer(), user.getServerInfo().getTopic());
                    GateSendMessageHolder.getInstance().sendMessage(messageWrap, user, gameTopic);
                    break;
                case scene_server:
                    if (user.getServerInfo().getPlayerId() == 0L) {
                        return;
                    }
                    String sceneTopic = getServerTopic(user.getServerInfo().getSceneServer(), user.getServerInfo().getTopic());
                    GateSendMessageHolder.getInstance().sendMessage(messageWrap, user, sceneTopic);
                    break;
                case gate_server:
                    Message message = messageWrap.decode();
                    if (Objects.isNull(message)) {
                        return;
                    }
                    dispatchProtocolService.dealMessage(messageWrap.cmd(),
                            method -> HandlerFactory.INSTANCE.createUserHandler(message, session.getId(), method));
                    break;
                default:
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getServerTopic(Server server, String topic) {
        return "%s-%d-%d".formatted(topic, server.getServerZone(), server.getId());
    }

}
