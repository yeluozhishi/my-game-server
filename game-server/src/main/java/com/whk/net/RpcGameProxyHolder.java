package com.whk.net;

import com.whk.rpc.consumer.GameRpcService;
import com.whk.rpc.consumer.proxy.RpcProxyHolder;
import com.whk.serverinfo.ServerManager;

public class RpcGameProxyHolder {

    private static final String rpcPosition = "com.whk.net.rpc";

    private static ServerManager serverManager;

    public static void init(ServerManager serverMgr, GameRpcService rpcService, String instanceId){
        RpcProxyHolder.INSTANCE.init(rpcService, instanceId, rpcPosition);
        serverManager = serverMgr;
    }


    public static <T> T getInstance(Class<?> clazz, int serverId){
        var server = serverManager.getServer(serverId);
        if (server.isPresent()){
            return RpcProxyHolder.INSTANCE.<T>getInstance(clazz, server.get().getInstanceId());
        } else {
            return null;
        }
    }
}
