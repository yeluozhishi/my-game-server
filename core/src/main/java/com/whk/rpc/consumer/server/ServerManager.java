package com.whk.rpc.consumer.server;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器列表管理
 */
public class ServerManager {

    // 服务器列表 serverId -> address
    private ConcurrentHashMap<Integer, Server> serverMap = new ConcurrentHashMap<Integer, Server>();

    private static ServerManager serverManager;

    private ServerManager() {
    }

    public static ServerManager getInstance() {
        if (serverManager == null) {
            synchronized (ServerManager.class) {
                if (serverManager == null) {
                    serverManager = new ServerManager();
                }
            }
        }
        return serverManager;
    }

    public ConcurrentHashMap<Integer, Server> getServerMap() {
        return serverMap;
    }

    public void addServer(Server server) {
        serverMap.put(server.id(), server);
    }

    public Optional<Server> getServer(int serverId) {
        return Optional.ofNullable(serverMap.get(serverId));
    }

}

