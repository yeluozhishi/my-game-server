package com.whk.server;

import com.google.gson.internal.LinkedTreeMap;
import com.whk.constant.HttpConstants;
import com.whk.net.enity.MapBean;
import com.whk.net.http.HttpClient;
import com.whk.serverinfo.Server;
import com.whk.serverinfo.ServerManager;
import com.whk.util.GsonUtil;
import com.whk.util.Util;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public GateServerManager(DiscoveryClient discoveryClient, String instanceId) {
        super(instanceId);
        this.discoveryClient = discoveryClient;
        requestServers();
    }

    @Override
    public void requestServers() {
        var res = HttpClient.getRestTemplate().postForObject(HttpConstants.WEB_CENTER.getHttpAndInfo() + HttpConstants.SERVER_LIST.getInfo(),
                Map.of("zone", 1, "token", getToken()), String.class);
        assert res != null;
        var data = GsonUtil.INSTANCE.<List<LinkedTreeMap>>GsonToMaps(res).get("serverList");

        var list = new ArrayList<MapBean>();
        data.forEach(f -> {
            var map = MapBean.MapBean(f);
            list.add(map);
        });

        var temp =  list.stream().collect(Collectors.toMap(f -> f.getDoubleToInt("id"),
                f -> new Server(f.getDoubleToInt("id"), f.getDoubleToInt("zone"), f.getDoubleToInt("serverType"), f.getString("serverName"),
                        f.getLocalDateTime("openServerTime", Util.getFormatter1()), f.getLocalDateTime("openEntranceTime", Util.getFormatter1()))));

        var instances = discoveryClient.getInstances("game-server");
        var fixTemp = new HashMap<Integer, Server>();
        instances.forEach(i -> {
            var s = temp.get(Integer.parseInt(i.getMetadata().get("id")));
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
