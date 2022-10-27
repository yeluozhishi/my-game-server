package com.whk.service;

import com.whk.config.GatewayServerConfig;
import com.whk.net.Message;
import com.whk.net.dispatchmessage.DispatchGameMessageService;
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

    private DispatchGameMessageService dispatchGameMessageService;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Autowired
    public void setConfig(GatewayServerConfig config) {
        this.config = config;
    }

    @Autowired
    public void setDispatchGameMessageService(DispatchGameMessageService dispatchGameMessageService) {
        this.dispatchGameMessageService = dispatchGameMessageService;
    }

    public void initServerManager() {
        this.serverManager = new ServerManager(config);

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
            dispatchGameMessageService.dealMessage(message);
            message.setGroupId(config.getKafkaConfig().getGroupId());
            message.setServerId(config.getKafkaConfig().getServer());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        transmit(message);
    }

    /**
     * 协议转发
     *
     * @param message 消息
     */
    private void transmit(Message message) {
        if (message.getComeFromClient()) {
            // 来自客户端，转发给服务器
            var user = UserMgr.INSTANCE.getUser(message.getUserIds().get(0));
            if (user.isPresent()) {
                if (user.get().getToServerId() != 0) {
                    // 跳转的服务器
                    if (serverManager.containsServer(user.get().getToServerId())) {
                        GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, message, String.valueOf(user.get().getToServerId()));
                    } else {
                        logger.warning("not exist to sever id:" + user.get().getToServerId());
                    }
                } else {
                    // 本服
                    if (serverManager.containsServer(user.get().getServerId())) {
                        GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, message, String.valueOf(user.get().getServerId()));
                    } else {
                        logger.warning("not exist sever id:" + user.get().getServerId());
                    }
                }
            }
        }
    }
}
