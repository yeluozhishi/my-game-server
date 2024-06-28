package com.whk.server;

import com.whk.config.KafkaConfig;
import org.whk.message.Server;
import com.whk.serverinfo.ServerManager;
import org.springframework.cloud.client.discovery.DiscoveryClient;

/**
 * 网关服务器列表
 */
public class GameServerManager extends ServerManager {

    private final DiscoveryClient discoveryClient;

    private final KafkaConfig config;

    public GameServerManager(KafkaConfig config, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.config = config;
    }

    @Override
    public void requestServers() {
        var instances = discoveryClient.getInstances("game-gateway");
        instances.addAll(discoveryClient.getInstances("game-server"));
        instances.forEach(instance -> {
            var zone = Integer.parseInt(instance.getMetadata().getOrDefault("zone", "0"));
            var id = Integer.parseInt(instance.getMetadata().getOrDefault("id", "0"));
            if (zone == config.getGroupId() && !getServers().containsKey(id)){
                addServer(id, new Server());
            }
        });
    }
}
