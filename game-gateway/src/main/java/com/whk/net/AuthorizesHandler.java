package com.whk.net;


import com.whk.Auth0JwtUtils;
import com.whk.protobuf.message.MessageProto;
import com.whk.server.GateServerManager;
import com.whk.user.PlayerServerInfo;
import com.whk.user.User;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

/**
 * 客户端连接网关 授权处理
 */
public class AuthorizesHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = Logger.getLogger(AuthorizesHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageProto.Message message = (MessageProto.Message) msg;
        userLogin(message, ctx);
    }

    public void userLogin(MessageProto.Message message, ChannelHandlerContext ctx) {
        try {
            var body = message.getLoginReq();
            var token = body.getToken();
            if (Auth0JwtUtils.verify(token)) {
                var userId = Auth0JwtUtils.getClaims(token).get("userId").asLong();

                var serverOpt = GateServerManager.getInstance().getServer(body.getServerId());
                if (serverOpt.isPresent()) {
                    if (UserMgr.INSTANCE.containsUser(userId)) {
                        UserMgr.INSTANCE.removeUser(userId);
                    }
                    PlayerServerInfo serverInfo = new PlayerServerInfo(serverOpt.get(), serverOpt.get());
                    User user = new User(userId, ctx, serverInfo);
                    UserMgr.INSTANCE.addUser(user);
                    ctx.pipeline().remove(this);
                    logger.info("用户：%d 登录gate".formatted(userId));
                    return;
                }
                logger.info("用户：%d 登录gate 未找到Game Server: %d".formatted(userId, body.getServerId()));
            }

        } catch (Exception e) {
            logger.severe("用户登录异常： %s".formatted(e.getMessage()));
        }
        ctx.close();
    }

}
