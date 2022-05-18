package com.whk.server;

import com.whk.config.GatewayServerConfig;
import com.whk.constant.Constants;
import com.whk.net.ResponseEntity;
import com.whk.util.Auth0JwtUtils;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerManager{

    private RestTemplate restTemplate;

    private List<Server> servers = new LinkedList<>();

    private static GatewayServerConfig config;

    private static String token;

    public ServerManager(GatewayServerConfig config, RestTemplate restTemplate){
        this.config = config;
        this.restTemplate = restTemplate;
        requestServers();
    }

    /**
     * 请求服务器列表
     * 通过服务名请求只能在 Bean(SmartInitializingSingleton) 初始化后
     *
     * @return
     */
    public void requestServers() {
        var responseEntity = restTemplate.postForObject("http://" + Constants.WEB_CENTER.getInfo() + Constants.GATE_GET_SERVER_LIST.getInfo(),
                Map.of("zone", 0, "token", getToken()), ResponseEntity.class);
        servers = (List<Server>) responseEntity.getData();
        System.out.println(servers);
    }

    public static String getToken() {
        if (token == null || Auth0JwtUtils.isExpired(token)) {
            token = Auth0JwtUtils.sign(Map.of("instanceId", config.getInstanceId()));
        }
        return token;
    }

    public List<Server> getServers() {
        return servers;
    }
}
