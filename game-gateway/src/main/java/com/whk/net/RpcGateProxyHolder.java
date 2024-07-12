package com.whk.net;

import com.whk.SpringUtils;
import com.whk.config.GatewayServerConfig;
import com.whk.constant.TopicConstants;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.consumer.proxy.RpcProxyHolder;
import com.whk.server.GateServerManager;

public class RpcGateProxyHolder {

    private static final String rpcPosition = "com.whk.net.rpc";

    private static GatewayServerConfig gatewayServerConfig;

    public static void init(GameRpcService rpcService, String topic) {
        RpcProxyHolder.INSTANCE.init(rpcService, topic, rpcPosition);
        gatewayServerConfig = SpringUtils.getBean(GatewayServerConfig.class);
    }


    public static <T extends IRpcService> T getInstance(Class<T> clazz, Integer serverId) {
        var server = GateServerManager.getInstance().getServer(serverId);
        return server.map(value ->
                (T) RpcProxyHolder.INSTANCE.getInstance(clazz, gatewayServerConfig.getTopic(value.getId())))
                .orElse(null);
    }

    public static String gateTopic() {
        return gatewayServerConfig.getTopic();
    }
}
