package com.whk.service;

import com.whk.config.GatewayServerConfig;
import com.whk.net.dispatchprotocol.DispatchProtocolService;
import com.whk.server.GateServerManager;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.whk.protobuf.message.MessageOuterClass;
import org.whk.protobuf.message.MessageWrapperOuterClass;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

@Component
public class ServerConnector {

    private final Logger logger = Logger.getLogger(ServerConnector.class.getName());

    private GateServerManager serverManager;

    private DiscoveryClient discoveryClient;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public void init(GatewayServerConfig config) {
        this.serverManager = new GateServerManager(discoveryClient);
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
     * 发送消息
     */
    public void sendMessage(MessageOuterClass.Message message, ChannelHandlerContext ctx) {
        MessageWrapperOuterClass.MessageWrapper wrapper;
        Long userId = 0L;
        if (ctx.channel().<Long>hasAttr(UserMgr.INSTANCE.ATTR_USER_ID)){
            userId = Long.getLong(ctx.channel().<Long>attr(UserMgr.INSTANCE.ATTR_USER_ID).get().toString());
        }

        wrapper = UserMgr.INSTANCE.WrapperMessage(message, userId);

        try {
            /* 网关消息处理 */
            UserMgr.INSTANCE.userLogin(wrapper, ctx, serverManager.getServers());
            DispatchProtocolService.getInstance().dealMessage(wrapper);
        } catch (InvocationTargetException | IllegalAccessException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        transmit(wrapper);

    }

    /**
     * 协议转发
     * 来自客户端，转发给服务器
     * @param message 消息
     */
    private void transmit(MessageWrapperOuterClass.MessageWrapper message) {
        if (message.getPlayerId() == 0L) {
            return;
        }
        var user = UserMgr.INSTANCE.getUserByPlayerId(message.getPlayerId());
        user.ifPresent(value -> {
            if (serverManager.containsServer(value.getServerId())) {
                UserMgr.INSTANCE.sendToServerMessage(message);
            } else {
                logger.warning("not exist to sever id:" + value.getServerId());
            }
        });
    }

}
