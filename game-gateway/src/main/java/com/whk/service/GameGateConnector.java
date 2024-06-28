package com.whk.service;

import com.whk.threadpool.dispatchprotocol.DispatchProtocolService;
import com.whk.server.GateServerManager;
import com.whk.threadpool.event.EventFactory;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.whk.protobuf.message.MessageProto;
import org.whk.protobuf.message.MessageWrapperProto;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

@Component
public class GameGateConnector {

    private final Logger logger = Logger.getLogger(GameGateConnector.class.getName());

    private GateServerManager serverManager;

    private DiscoveryClient discoveryClient;

    private DispatchProtocolService dispatchProtocolService;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public void init() {
        this.serverManager = new GateServerManager(discoveryClient);
        dispatchProtocolService = new DispatchProtocolService();
    }

    public GateServerManager getServerManager() {
        return serverManager;
    }

    public void reload() {
        reloadServer();
    }

    /**
     * 重载配置的游戏服务器
     */
    private void reloadServer() {
        try {
            serverManager.requestServers();
        } catch (Exception e) {
            logger.warning("服务未初始化完成！");
        }
    }

    /**
     * 消费客户端消息
     */
    public void consumerClientMessage(MessageProto.Message message, ChannelHandlerContext ctx) {

        long userId = 0L;
        if (ctx.channel().hasAttr(UserMgr.INSTANCE.ATTR_USER_ID)){
            userId = Long.getLong(ctx.channel().attr(UserMgr.INSTANCE.ATTR_USER_ID).get().toString());
        }

        try {
            /* 网关消息处理 */
            UserMgr.INSTANCE.userLogin(message, ctx, serverManager.getServers());
            if (!UserMgr.INSTANCE.passCheck(userId)){
                ctx.close();
                return;
            }
            long finalUserId = userId;
            dispatchProtocolService.dealMessage(message, finalUserId,
                    method -> EventFactory.INSTANCE.createUserEvent(message, finalUserId, method));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        transmit(UserMgr.INSTANCE.WrapperMessage(message, userId));

    }

    /**
     * 协议转发
     * 来自客户端，转发给服务器
     * @param message 消息
     */
    private void transmit(MessageWrapperProto.MessageWrapper message) {
        if (message.getPlayerId() == 0L) {
            return;
        }
        var user = UserMgr.INSTANCE.getUserByPlayerId(message.getPlayerId());
        user.ifPresent(value -> {
            if (serverManager.containsServer(value.getServerId())) {
                UserMgr.INSTANCE.sendToServerMessage(message);
            } else {
                logger.warning("not exist to sever id:%d".formatted(value.getServerId()));
            }
        });
    }

}
