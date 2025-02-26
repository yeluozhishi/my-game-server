package com.whk.serverinfo;

import com.whk.message.Server;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器管理
 */

@Getter
@Slf4j
public abstract class ServerManager {

    private final Map<Integer, Server> servers = new ConcurrentHashMap<>();

    @Setter
    private Server localHost;

    public Optional<Server> getServer(Integer key) {
        return Optional.ofNullable(servers.get(key));
    }

    public void addServer(Integer key, Server server) {
        servers.put(key, server);
        log.info("server add :%s ".formatted(server.toString()));
    }

    /**
     * 请求服务器列表
     */
    public abstract void updateOnlineServers();

}
