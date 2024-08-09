package com.whk.server;

import com.whk.message.Server;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.rpc.api.IRpcServerInfoService;
import com.whk.serverinfo.ServerManager;
import lombok.Getter;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 网关服务器列表
 */
public class GameServerManager extends ServerManager {

    @Getter
    private static final GameServerManager instance = new GameServerManager();

    private DiscoveryClient discoveryClient;

    private Map<Integer, Server> gateServers = new HashMap<>();

    private int zone;

    public void init(int zone, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.zone = zone;
        requestServers();
    }

    public void updateGate(){
        var instances = discoveryClient.getInstances("game-gateway").stream()
                .filter(serviceInstance -> zone == Integer.parseInt(serviceInstance.getMetadata().getOrDefault("zone", "0"))).toList();
        if (instances.size() == gateServers.size()) return;
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
    }

    @Override
    public void requestServers() {
        if (gateServers.isEmpty()) {
            updateGate();
            return;
        }
        var gate = gateServers.values().iterator().next();

        var servers = RpcGameProxyHolder.getInstance(IRpcServerInfoService.class, gate.getId()).getServers();
        servers.forEach(this::addServer);
    }
}
