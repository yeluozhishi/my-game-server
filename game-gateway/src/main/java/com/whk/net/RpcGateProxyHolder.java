package com.whk.net;

import com.whk.config.GatewayServerConfig;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.server.GateServerManager;

public class RpcGateProxyHolder {

    private static GatewayServerConfig gatewayServerConfig;

    public static void init(KafkaMessageService kafkaMessageService, GatewayServerConfig config) {
        gatewayServerConfig = config;
        var rpcService = new GameRpcService(kafkaMessageService);
        RpcProxyHolder.INSTANCE.init(rpcService, gatewayServerConfig.getRpcResponseTopic());
    }


    public static <T extends IRpcService> T getInstance(Class<T> clazz, Integer serverId) {
        return GateServerManager.getInstance().getServer(serverId).map(value ->
                        (T) RpcProxyHolder.INSTANCE.getInstance(clazz, gatewayServerConfig.getRpcRequestTopic(value.getId()), 0))
                .orElse(null);
    }
}
