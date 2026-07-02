package com.whk.net;


import com.google.protobuf.Message;
import com.whk.Auth0JwtUtils;
import com.whk.MessageWrap;
import com.whk.SpringUtils;
import com.whk.config.GatewayServerConfig;
import com.whk.protobuf.message.LoginProto;
import com.whk.server.GateServerManager;
import com.whk.user.PlayerServerInfo;
import com.whk.user.User;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 客户端连接网关 授权处理
 */
@Slf4j
public class AuthorizesHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MessageWrap messageWrap = (MessageWrap) msg;

        try {
            Message message = messageWrap.decode();
            if (Objects.isNull(message)) {
                ctx.close();
                return;
            }
            LoginProto.ReqLogin req = (LoginProto.ReqLogin) message;
            var token = req.getToken();
            if (Auth0JwtUtils.verify(token)) {
                var userId = req.getUserId();
                var server = GateServerManager.getInstance().getServer(req.getServerId());
                if (Objects.nonNull(server)) {
                    if (UserMgr.INSTANCE.containsUser(userId)) {
                        UserMgr.INSTANCE.logOut(userId);
                    }
                    PlayerServerInfo serverInfo = new PlayerServerInfo(server);
                    GatewayServerConfig serverConfig = SpringUtils.getBean(GatewayServerConfig.class);
                    serverInfo.setTopic(serverConfig.getKafkaConfig().getMessageTopic());
                    User user = new User(userId, ctx, serverInfo);
                    UserMgr.INSTANCE.addUser(user);
                    ctx.pipeline().remove(this);
                    log.info("用户：%d 登录gate".formatted(userId));
                    MessageUtil.getInstance().sendToClientMessage(LoginProto.ResLogin.newBuilder().setToken(token).build(), userId);
                    return;
                }
                log.info("用户：%d 登录gate 未找到Game Server: %d".formatted(userId, req.getServerId()));
            }

        } catch (Exception e) {
            log.error("用户登录异常： %s".formatted(e.getMessage()));
        }
        ctx.close();
    }

}
