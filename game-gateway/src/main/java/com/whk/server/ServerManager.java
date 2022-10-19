package com.whk.server;

import com.whk.config.GatewayServerConfig;
import com.whk.constant.Constants;
import com.whk.http.HttpClient;
import com.whk.util.Auth0JwtUtils;
import com.whk.util.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
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
        var res = HttpClient.getRestTemplate().<String>postForObject(Constants.WEB_CENTER.getHttpAndInfo() + Constants.GATE_GET_SERVER_LIST.getInfo(),
                Map.of("zone", 0, "token", getToken()), String.class);
        assert res != null;
        var data = GsonUtil.INSTANCE.<ArrayList<Server>>GsonToMaps(res).get("data");
        data.forEach(server -> servers.put(server.getId(), server));
//        var serverList = GsonUtil.INSTANCE.<String>GsonToListMaps(data);
//        servers = serverList.stream().collect(Collectors.toMap(m -> Integer.parseInt(m.get("id")),
//                m -> new Server(Integer.parseInt(m.get("id")), m.get("serverName"),
//                        m.get("ip"), Integer.parseInt(m.get("port")), Integer.parseInt(m.get("zone")))));
        System.out.println("server list" + servers);
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
