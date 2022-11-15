package com.whk.service;

import com.whk.config.GatewayServerConfig;
import com.whk.net.enity.Message;
import com.whk.net.dispatchprotocol.DispatchProtocolService;
import com.whk.net.kafka.GameMessageInnerDecoder;
import com.whk.server.ServerManager;
import com.whk.user.UserMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

@Component
public class ServerConnector {

    private final Logger logger = Logger.getLogger(ServerConnector.class.getName());

    private ServerManager serverManager;

    private GatewayServerConfig config;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private DispatchProtocolService dispatchProtocolService;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Autowired
    public void setDispatchProtocolService(DispatchProtocolService dispatchProtocolService) {
        this.dispatchProtocolService = dispatchProtocolService;
    }

    public void initServerManager(GatewayServerConfig config) {
        this.config = config;
        this.serverManager = new ServerManager(config);
    }

    public KafkaTemplate<String, byte[]> getKafkaTemplate() {
        return kafkaTemplate;
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
    public void sendMessage(Message message) {
        try {
            /* 网关消息处理 */
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
        var user = UserMgr.INSTANCE.getUserByPlayerId(message.getPlayerId());
        user.ifPresent(value -> {
            if (value.getServerId() != 0) {
                // 跳转的服务器
                if (serverManager.containsServer(value.getServerId())) {
                    GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, message, value.getServerId());
                } else {
                    logger.warning("not exist to sever id:" + value.getServerId());
                }
            } else {
                // 本服
                if (serverManager.containsServer(value.getServerId())) {
                    GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, message, value.getServerId());
                } else {
                    logger.warning("not exist sever id:" + value.getServerId());
                }
            }
        });
    }

}
