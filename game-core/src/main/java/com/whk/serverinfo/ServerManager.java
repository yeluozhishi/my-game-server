package com.whk.serverinfo;

import com.whk.util.Auth0JwtUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 服务器管理
 */
public abstract class ServerManager {

    private Map<Integer, Server> servers = new HashMap<>();

    private String token;

    private final String instanceId;

    public ServerManager(String instanceId){
        this.instanceId = instanceId;
    }

    public Map<Integer, Server> getServers() {
        return servers;
    }

    public void setServers(Map<Integer, Server> servers) {
        synchronized (this.servers) {
            this.servers = servers;
        }
    }

    public Optional<Server> getServer(Integer key) {
        return Optional.ofNullable(servers.get(key));
    }

    public void addServer(Integer key, Server server){
        synchronized (servers) {
            servers.put(key, server);
        }
    }

    /**
     * 请求服务器列表
     * 通过服务名请求只能在 Bean(SmartInitializingSingleton) 初始化后
     *
     */
    public abstract void requestServers();

    public String getToken() {
        if (token == null || Auth0JwtUtils.isExpired(token)) {
            token = Auth0JwtUtils.sign(Map.of("instanceId", instanceId));
        }
        return token;
    }

    public Boolean containsServer(int id){
        return servers.containsKey(id);
    }
}
