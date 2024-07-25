package com.whk.server;

import com.whk.listener.ListenerRegister;
import com.whk.net.http.HttpClient;
import com.whk.message.Server;
import com.whk.serverinfo.ServerManager;
import lombok.Getter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.whk.message.ReqServerListMessage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 游戏服列表
 */
public class GateServerManager extends ServerManager {

    @Getter
    private static final GateServerManager instance = new GateServerManager();

    private int zone = 0;

    private Map<Integer, Server> centerServers = new HashMap<>();

    /**
     * 服务发现客户端实例
     */
    private DiscoveryClient discoveryClient;


    public void init(DiscoveryClient discoveryClient, int zone) {
        this.discoveryClient = discoveryClient;
        this.zone = zone;

        ReqServerListMessage message = new ReqServerListMessage();
        message.setZone(zone);
        centerServers = HttpClient.getInstance().getServerList(message)
                .stream().collect(Collectors.toMap(Server::getId, f -> f));

        ListenerRegister.INSTANCE.registerHeartbeatListener(this);
    }

    @Override
    public void requestServers() {
        var instances = discoveryClient.getInstances("game-server");
        instances.forEach(i -> {
            var server = centerServers.get(Integer.parseInt(i.getMetadata().getOrDefault("id", "0")));
            if (Objects.nonNull(server) && server.getServerZone() == zone && !getServers().containsKey(server.getId())) {
                server.setInstanceId(i.getInstanceId());
                addServer(server.getId(), server);
            }
        });
    }

}
