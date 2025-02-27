package com.whk.serverinfo;

import com.whk.message.Server;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器管理
 */

@Getter
@Slf4j
public abstract class ServerManager {

    private final Map<Integer, Server> servers = new ConcurrentHashMap<>();

    private final Map<Integer, Map<Integer, Server>> groupServers = new ConcurrentHashMap<>();

    @Setter
    private Server localHost;

    public Server getServer(Integer key) {
        return servers.get(key);
    }

    public Server getServer(Integer key, ServerType serverType) {
        return groupServers.getOrDefault(serverType.getType(), new ConcurrentHashMap<>()).get(key);
    }

    public Map<Integer, Server> getGroupServers(ServerType serverType) {
        return groupServers.getOrDefault(serverType.getType(), new ConcurrentHashMap<>());
    }

    public void addServer(Integer key, Server server) {
        servers.put(key, server);
        groupServers.computeIfAbsent(server.getServerType(), _ -> new ConcurrentHashMap<>()).put(key, server);
        log.info("server add :%s ".formatted(server.toString()));
    }

    public void removeServer(Integer key) {
        var server = servers.remove(key);
        if (Objects.nonNull(server)) {
            groupServers.computeIfPresent(server.getServerType(), (_, v) -> {
                v.remove(key);
                return v;
            });
        }
        log.info("server remove :%s ".formatted(key));
    }

    /**
     * 请求服务器列表
     */
    public abstract void updateOnlineServers(boolean update);
}
