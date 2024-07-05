package com.whk.serverinfo;

import com.whk.message.Server;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * 服务器管理
 */

@Getter
public abstract class ServerManager {

    private final Logger logger = Logger.getLogger(ServerManager.class.getName());

    private final Map<Integer, Server> servers = new ConcurrentHashMap<>();

    public Optional<Server> getServer(Integer key) {
        return Optional.ofNullable(servers.get(key));
    }

    public void addServer(Integer key, Server server) {
        servers.put(key, server);
        logger.info("server add :%s".formatted(server));
    }

    /**
     * 请求服务器列表
     */
    public abstract void requestServers();

    public Boolean containsServer(Integer id) {
        return servers.containsKey(id);
    }
}
