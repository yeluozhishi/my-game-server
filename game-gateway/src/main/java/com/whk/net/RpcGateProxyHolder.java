package com.whk.net;

import com.whk.config.GatewayServerConfig;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.server.GateServerManager;

public class RpcGateProxyHolder {

    private static GatewayServerConfig gatewayServerConfig;

    public static void init(GameRpcService rpcService, GatewayServerConfig config) {
        gatewayServerConfig = config;
        RpcProxyHolder.INSTANCE.init(rpcService, gatewayServerConfig.getRpcResponseTopic());
    }


    public static <T extends IRpcService> T getInstance(Class<T> clazz, Integer serverId) {
        var server = GateServerManager.getInstance().getServer(serverId);
        return server.map(value ->
                (T) RpcProxyHolder.INSTANCE.getInstance(clazz, gatewayServerConfig.getRpcRequestTopic(value.getId())))
                .orElse(null);
    }
}
