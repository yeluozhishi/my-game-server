package com.whk.server;

import com.whk.message.Server;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.rpc.api.gate.IRpcGateServerInfoService;
import com.whk.serverinfo.ServerManager;
import com.whk.tick.WorldTick;
import lombok.Getter;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * 网关服务器列表
 */
public class GameServerManager extends ServerManager {

    private Logger logger = Logger.getLogger(GameServerManager.class.getName());

    @Getter
    private static final GameServerManager instance = new GameServerManager();

    private DiscoveryClient discoveryClient;

    private final Map<Integer, Server> gateServers = new HashMap<>();

    private int zone;

    private Set<Integer> newAddGateServerIds;

    public void init(int zone, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.zone = zone;
    }

    public void gateNoticeUpdateServer(int gateServerId){
        newAddGateServerIds.add(gateServerId);
        updateGate(true);
        if (!gateServers.keySet().containsAll(newAddGateServerIds)){
            WorldTick.INSTANCE.onceTask(() -> gateNoticeUpdateServer(gateServerId), 20);
            return;
        }
        newAddGateServerIds.remove(gateServerId);
    }

    public void updateGate(boolean notice) {
        logger.info("开始获取网关服务器配置");
        var instances = discoveryClient.getInstances("game-gateway").stream()
                .filter(serviceInstance -> zone == Integer.parseInt(serviceInstance.getMetadata().getOrDefault("zone", "0")))
                .toList();

        if (!notice && instances.isEmpty()) {
            // 至少需要获取一个网关
            logger.info("获取网关服务器配置失败，开始重试");
            WorldTick.INSTANCE.onceTask(() -> updateGate(notice), 20);
            return;
        }

        instances.forEach(serviceInstance -> {
            int id = Integer.parseInt(serviceInstance.getMetadata().getOrDefault("id", "0"));
            Server server = new Server();
            server.setId(id);
            server.setServerZone(zone);
            server.setInstanceId(serviceInstance.getInstanceId());
            server.setServerType(2);
            gateServers.put(id, server);
            addServer(id, server);
        });
        updateOnlineServers();
    }

    @Override
    public void updateOnlineServers() {
        var gate = gateServers.values().iterator().next();
        var servers = RpcGameProxyHolder.getInstance(IRpcGateServerInfoService.class, gate.getId()).getServers();
        servers.forEach(this::addServer);
        logger.info("获取网关服务器配置完成");
    }
}
