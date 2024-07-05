package com.whk.server;

import com.whk.config.KafkaConfig;
import com.whk.message.Server;
import com.whk.serverinfo.ServerManager;
import lombok.Getter;
import org.springframework.cloud.client.discovery.DiscoveryClient;

/**
 * 网关服务器列表
 */
public class GameServerManager extends ServerManager {

    @Getter
    private static final GameServerManager instance = new GameServerManager();

    private DiscoveryClient discoveryClient;

    private int zone;

    public void init(int zone, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.zone = zone;
    }

    @Override
    public void requestServers() {
        var instances = discoveryClient.getInstances("game-gateway");
        instances.addAll(discoveryClient.getInstances("game-server"));
        instances.forEach(instance -> {
            var zone = Integer.parseInt(instance.getMetadata().getOrDefault("zone", "0"));
            var id = Integer.parseInt(instance.getMetadata().getOrDefault("id", "0"));
            if (this.zone == zone && !getServers().containsKey(id)) {
                addServer(id, new Server());
            }
        });
    }
}
