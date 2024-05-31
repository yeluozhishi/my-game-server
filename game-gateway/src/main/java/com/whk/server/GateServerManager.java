package com.whk.server;

import com.whk.constant.HttpConstants;
import com.whk.net.http.HttpClient;
import com.whk.serverinfo.Server;
import com.whk.serverinfo.ServerManager;
import org.whk.GsonUtil;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.whk.message.MapBean;

import java.time.format.DateTimeFormatter;
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
        assert res != null;
        var data = GsonUtil.INSTANCE.jsonToList(res, MapBean.class);

        var temp =  data.stream().collect(Collectors.toMap(f -> f.getString("instanceId"),
                f -> new Server(f.getDoubleToInt("id"), f.getDoubleToInt("zone"), f.getString("instanceId"), f.getDoubleToInt("serverType"), f.getString("serverName"),
                        f.getLocalDateTime("openServerTime", DateTimeFormatter.ISO_OFFSET_DATE_TIME), f.getLocalDateTime("openEntranceTime", DateTimeFormatter.ISO_OFFSET_DATE_TIME))));

        var instances = discoveryClient.getInstances("game-server");
        var fixTemp = new HashMap<Integer, Server>();
        instances.forEach(i -> {
            var s = temp.get(i.getInstanceId());
            if (s != null){
                fixTemp.put(s.getId(), s);
            }
        });

        if (!fixTemp.isEmpty()){
            setServers(fixTemp);
            System.out.println("server list updated");
        }
    }
}
