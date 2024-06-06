package com.whk.server;

import com.whk.constant.HttpConstants;
import com.whk.net.http.HttpClient;
import com.whk.serverinfo.Server;
import com.whk.serverinfo.ServerManager;
import com.whk.threadpool.ThreadPoolManager;
import org.whk.GsonUtil;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.HashMap;
import java.util.Map;
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
        var res = HttpClient.getRestTemplate().postForObject(HttpConstants.WEB_CENTER.getHttpAndInfo() + HttpConstants.SERVER_LIST.getInfo(),
                Map.of("zone", 1, "token", HttpClient.getToken()), String.class);

        var data = GsonUtil.INSTANCE.jsonToList(res, Server.class);

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
