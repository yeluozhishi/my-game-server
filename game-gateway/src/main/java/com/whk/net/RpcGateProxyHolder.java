package com.whk.net;

import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.consumer.proxy.RpcProxyHolder;
import com.whk.server.GateServerManager;

public class RpcGateProxyHolder {

    private static final String rpcPosition = "com.whk.net.rpc";

    public static void init(GameRpcService rpcService, String instanceId) {
        RpcProxyHolder.INSTANCE.init(rpcService, instanceId, rpcPosition);
    }


    public static <T extends IRpcService> T getInstance(Class<T> clazz, Integer serverId) {
        var server = GateServerManager.getInstance().getServer(serverId);
        return server.map(value -> (T) RpcProxyHolder.INSTANCE.getInstance(clazz, value.getInstanceId())).orElse(null);
    }

    public static String getInstanceId() {
        return RpcProxyHolder.INSTANCE.getInstanceId();
    }
}
