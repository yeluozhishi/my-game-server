package com.whk.service;

import com.whk.config.GatewayServerConfig;
import com.whk.server.ServerManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ServerConnector {

    private Logger logger = Logger.getLogger(ServerConnector.class.getName());

    // serverId -> Channel
    private ConcurrentHashMap<Integer, Channel> serverChannel = new ConcurrentHashMap<>();

    private ServerManager serverManager;

    private RestTemplate restTemplate;

    private GatewayServerConfig config;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setConfig(GatewayServerConfig config) {
        this.config = config;
    }

    public ServerManager initServerManager() {
        this.serverManager = new ServerManager(config, restTemplate);
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
     * 连接游戏服务器 whk
     */
    private void connectServer() {
        if (serverManager.getServers().size() != 0) {
            var servers = serverManager.getServers();
            var notContain = servers.stream().filter(f -> serverChannel.containsKey(f.getId()));
            notContain.forEach(p -> {

            });
        }
    }

    /**
     * 移除Channel
     */
    private void removeChannel() {
        var serverMap = serverManager.getServers().stream().map(f -> f.getId()).collect(Collectors.toSet());
        Set<Integer> needRemove = new HashSet<>();
        serverChannel.forEach((p, channel) -> {
            if (!serverMap.contains(p)) {
                needRemove.add(p);
            }
        });
        needRemove.forEach(m -> {
            var channel = serverChannel.remove(m);
            channel.closeFuture();
        });
    }

}
