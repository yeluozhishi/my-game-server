package com.whk.net;

import com.whk.config.GameServerConfig;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.server.GameServerManager;

public class RpcGameProxyHolder {

    private static GameServerConfig gameServerConfig;

    public static void init(GameRpcService rpcService, GameServerConfig config) {
        gameServerConfig = config;
        RpcProxyHolder.INSTANCE.init(rpcService, gameServerConfig.getRpcResponseTopic());
    }

    public static <T extends IRpcService> T getInstance(Class<T> clazz, int serverId) {
        var server = GameServerManager.getInstance().getServer(serverId);
        return server.map(value -> (T) RpcProxyHolder.INSTANCE.getInstance(clazz, gameServerConfig.getRpcRequestTopic(value.getId())))
                .orElse(null);
    }
}
