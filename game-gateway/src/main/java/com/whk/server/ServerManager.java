package com.whk.server;

import com.whk.config.GatewayServerConfig;
import com.whk.constant.Constants;
import com.whk.net.ResponseEntity;
import com.whk.util.Auth0JwtUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ServerManager{

    private final RestTemplate restTemplate;

    private Map<Integer, Server> servers = new HashMap<>();

    private static GatewayServerConfig config;

    private static String token;

    public ServerManager(GatewayServerConfig config, RestTemplate restTemplate){
        ServerManager.config = config;
        this.restTemplate = restTemplate;
        requestServers();
    }

    /**
     * 请求服务器列表
     * 通过服务名请求只能在 Bean(SmartInitializingSingleton) 初始化后
     *
     */
    public void requestServers() {
        ResponseEntity responseEntity = restTemplate.postForObject("http://" + Constants.WEB_CENTER.getInfo() + Constants.GATE_GET_SERVER_LIST.getInfo(),
                Map.of("zone", 0, "token", getToken()), ResponseEntity.class);
        assert responseEntity != null;
        var temp = responseEntity.getData();
        servers = temp.stream().collect(Collectors.toMap(Server::getId, Function.identity()));
        System.out.println(temp);
    }

    public static String getToken() {
        if (token == null || Auth0JwtUtils.isExpired(token)) {
            token = Auth0JwtUtils.sign(Map.of("instanceId", config.getInstanceId()));
        }
        return token;
    }

    public Boolean containsServer(int id){
        return servers.containsKey(id);
    }
}
