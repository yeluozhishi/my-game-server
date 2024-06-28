package com.whk.server;

import com.whk.net.http.HttpClient;
import org.whk.message.Server;
import com.whk.serverinfo.ServerManager;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.whk.message.ReqServerListMessage;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 游戏服列表
 */
public class GateServerManager extends ServerManager {

    /**
     * 服务发现客户端实例
     */
    private final DiscoveryClient discoveryClient;

    public GateServerManager(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        requestServers();
    }

    @Override
    public void requestServers() {
        ReqServerListMessage message = new ReqServerListMessage();
        message.setZone(1);
        List<Server> data = HttpClient.getInstance().getServerList(message, Server.class);

        var temp =  data.stream().collect(Collectors.toMap(Server::getServerId, f -> f));

        var instances = discoveryClient.getInstances("game-server");
        var fixTemp = new HashMap<Integer, Server>();
        instances.forEach(i -> {
            var s = temp.get(i.getInstanceId());
            if (s != null){
                fixTemp.put(s.getServerId(), s);
            }
        });

        if (!fixTemp.isEmpty()){
            setServers(fixTemp);
            System.out.println("server list updated");
        }
    }
}
