package com.whk.service;

import com.whk.config.GatewayServerConfig;
import com.whk.net.dispatchprotocol.DispatchProtocolService;
import com.whk.net.enity.Message;
import com.whk.server.GateServerManager;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

@Component
public class ServerConnector {

    private final Logger logger = Logger.getLogger(ServerConnector.class.getName());

    private GateServerManager serverManager;

    private DispatchProtocolService dispatchProtocolService;

    private DiscoveryClient discoveryClient;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setDispatchProtocolService(DispatchProtocolService dispatchProtocolService) {
        this.dispatchProtocolService = dispatchProtocolService;
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
    public void sendMessage(Message message, ChannelHandlerContext ctx) {
        try {
            /* 网关消息处理 */
            UserMgr.INSTANCE.userLogin(message, ctx, serverManager.getServers());
            dispatchProtocolService.dealMessage(message);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        transmit(message);
    }

    /**
     * 协议转发
     * 来自客户端，转发给服务器
     * @param message 消息
     */
    private void transmit(Message message) {
        if (message.getPlayerId() == null) {
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
