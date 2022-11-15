package com.whk.server;

import com.google.gson.internal.LinkedTreeMap;
import com.whk.config.GatewayServerConfig;
import com.whk.constant.HttpConstants;
import com.whk.net.http.HttpClient;
import com.whk.net.enity.MapBean;
import com.whk.serverinfo.Server;
import com.whk.util.Auth0JwtUtils;
import com.whk.util.GsonUtil;
import com.whk.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerManager{

    private Map<Integer, Server> servers = new HashMap<>();

    private static GatewayServerConfig config;

    private static String token;

    public ServerManager(GatewayServerConfig config){
        ServerManager.config = config;
        requestServers();
    }

    /**
     * 请求服务器列表
     * 通过服务名请求只能在 Bean(SmartInitializingSingleton) 初始化后
     *
     */
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

        if (!temp.isEmpty()){
            servers = temp;
            System.out.println("server list updated");
        }
    }

    public static String getToken() {
        if (token == null || Auth0JwtUtils.isExpired(token)) {
            token = Auth0JwtUtils.sign(Map.of("instanceId", config.getData().getInstanceId()));
        }
        return token;
    }

    public Boolean containsServer(int id){
        return servers.containsKey(id);
    }
}
