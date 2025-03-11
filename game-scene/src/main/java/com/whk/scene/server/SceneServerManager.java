package com.whk.scene.server;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.whk.message.Server;
import com.whk.net.rpc.api.gate.IRpcGateServerInfoService;
import com.whk.scene.config.GameDateConfig;
import com.whk.scene.net.RpcSceneProxyHolder;
import com.whk.serverinfo.ServerManager;
import com.whk.serverinfo.ServerType;
import com.whk.tick.WorldTick;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.Set;

/**
 * 服务器列表
 */
@Slf4j
public class SceneServerManager extends ServerManager {

    @Getter
    private static final SceneServerManager instance = new SceneServerManager();

    private DiscoveryClient discoveryClient;

    private GameDateConfig gameDateConfig;

    private final Set<Integer> newAddGateServerIds = new ConcurrentHashSet<>();

    public void init(GameDateConfig gameDateConfig, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.gameDateConfig = gameDateConfig;
    }

    public void gateNoticeUpdateServer(int gateServerId) {
        newAddGateServerIds.add(gateServerId);
        updateGate(false);
        if (!getGroupServers(ServerType.GATE).keySet().containsAll(newAddGateServerIds)) {
            WorldTick.INSTANCE.onceTask(() -> gateNoticeUpdateServer(gateServerId), 10);
            return;
        }
        newAddGateServerIds.remove(gateServerId);
    }

    public void updateGate(boolean update) {
        log.info("开始获取网关服务器配置");
        var instances = discoveryClient.getInstances("game-gateway").stream()
                .filter(serviceInstance -> gameDateConfig.getZone() == Integer.parseInt(serviceInstance.getMetadata().getOrDefault("zone", "0")))
                .toList();

        if (update && instances.isEmpty()) {
            // 至少需要获取一个网关
            log.info("获取网关服务器配置失败，开始重试");
            WorldTick.INSTANCE.onceTask(() -> updateGate(true), 10);
            return;
        }
        instances.forEach(serviceInstance -> {
            int id = Integer.parseInt(serviceInstance.getMetadata().getOrDefault("id", "0"));
            Server server = new Server();
            server.setId(id);
            server.setServerZone(gameDateConfig.getZone());
            server.setInstanceId(serviceInstance.getInstanceId());
            server.setServerType(1);
            addServer(id, server);
        });
        updateOnlineServers(update);
        setLocalHost(getServer(gameDateConfig.getServer()));
        addSelfToGate(update);
    }

    @Override
    public void updateOnlineServers(boolean update) {
        var gate = getGroupServers(ServerType.GATE).values().iterator().next();
        var servers = RpcSceneProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, gate.getId()).getServers();
        servers.forEach(this::addServer);
        setLocalHost(getServer(gameDateConfig.getServer()));
        log.info("获取网关服务器配置完成");
    }

    public void addSelfToGate(boolean update) {
        if (update) {
            getGroupServers(ServerType.GATE).values()
                    .forEach(gate -> RpcSceneProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, gate.getId()).updateServer());
        }
    }
}
