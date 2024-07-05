package com.whk.server;

import com.whk.net.http.HttpClient;
import com.whk.message.Server;
import com.whk.serverinfo.ServerManager;
import com.whk.threadpool.ThreadPoolManager;
import lombok.Getter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.whk.message.ReqServerListMessage;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 游戏服列表
 */
public class GateServerManager extends ServerManager {

    @Getter
    private static final GateServerManager instance = new GateServerManager();

    private int zone = 0;

    /**
     * 服务发现客户端实例
     */
    private DiscoveryClient discoveryClient;


    public void init(DiscoveryClient discoveryClient, int zone) {
        this.discoveryClient = discoveryClient;
        this.zone = zone;
    }

    @Override
    public void requestServers() {
        ReqServerListMessage message = new ReqServerListMessage();
        message.setZone(zone);
        List<Server> data = HttpClient.getInstance().getServerList(message);

        var temp = data.stream().collect(Collectors.toMap(Server::getId, f -> f));

        var instances = discoveryClient.getInstances("game-server");
        instances.forEach(i -> {
            var s = temp.get(Integer.parseInt(i.getMetadata().getOrDefault("id", "0")));
            if (s != null) {
                addServer(s.getId(), s);
            }
        });
    }

}
