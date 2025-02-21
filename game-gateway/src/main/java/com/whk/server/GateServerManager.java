package com.whk.server;

import com.whk.config.GatewayServerConfig;
import com.whk.net.RpcGateProxyHolder;
import com.whk.net.http.HttpClient;
import com.whk.message.Server;
import com.whk.serverinfo.ServerManager;
import com.whk.tick.WorldTick;
import lombok.Getter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.whk.message.gamegate.ReqServerListMessage;
import com.whk.net.rpc.api.IRpcServerInfoService;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 游戏服列表
 */
public class GateServerManager extends ServerManager {

    private final Logger logger = Logger.getLogger(GateServerManager.class.getName());

    @Getter
    private static final GateServerManager instance = new GateServerManager();

    private GatewayServerConfig serverConfig;

    private Map<Integer, Server> centerServers = new HashMap<>();

    /**
     * 服务发现客户端实例
     */
    private DiscoveryClient discoveryClient;


    public void init(DiscoveryClient discoveryClient, GatewayServerConfig serverConfig) {
        this.discoveryClient = discoveryClient;
        this.serverConfig = serverConfig;
    }

    public void getCenterServers() {
        logger.info("开始获取服务器配置");
        ReqServerListMessage message = new ReqServerListMessage();
        message.setZone(serverConfig.getData().getZone());
        message.setOpen(true);
        var serverList = HttpClient.getInstance().getServerList(message);
        if (Objects.nonNull(serverList)) {
            centerServers = serverList.stream().collect(Collectors.toMap(Server::getId, f -> f));
        }
        updateOnlineServers();
    }

    @Override
    public void updateOnlineServers() {
        var instances = discoveryClient.getInstances("game-server");
        AtomicBoolean change = new AtomicBoolean(false);
        instances.forEach(i -> {
            var server = centerServers.get(Integer.parseInt(i.getMetadata().getOrDefault("id", "0")));
            if (Objects.nonNull(server) && server.getServerZone() == serverConfig.getData().getZone() && !getServers().containsKey(server.getId())) {
                server.setInstanceId(i.getInstanceId());
                addServer(server.getId(), server);
                change.set(true);
            }
        });

        Set<Integer> serverIds = getServers().keySet();

        for (Integer serverId : serverIds) {
            if (!centerServers.containsKey(serverId)) {
                getServers().remove(serverId);
                change.set(true);
            }
        }

        if (change.get()) {
            noticeServerUpdate();
        }
        if (instances.isEmpty() || centerServers.isEmpty() || getServers().size() != centerServers.size()) {
            logger.info("获取服务器配置失败，开始重试");
            WorldTick.INSTANCE.onceTask(this::getCenterServers, 10);
            return;
        }
        logger.info("获取服务器配置结束");
    }

    public void noticeServerUpdate() {
        for (Server server : getServers().values()) {
            RpcGateProxyHolder.getInstance(IRpcServerInfoService.class, server.getId()).updateServerInfo(serverConfig.getData().getServer());
        }
    }

}
