package com.whk.net;

import com.whk.config.GatewayServerConfig;
import com.whk.message.Server;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.net.rpc.proxy.RpcServerProxy;
import com.whk.server.GateServerManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcGateProxyHolder extends RpcServerProxy {

    private static final RpcGateProxyHolder INSTANCE = new RpcGateProxyHolder();


    private GatewayServerConfig gatewayServerConfig;

    private RpcGateProxyHolder() {
    }

    public static RpcGateProxyHolder getInstance() {
        return INSTANCE;
    }

    public void init(KafkaMessageService kafkaMessageService, GatewayServerConfig config) {
        gatewayServerConfig = config;
        var rpcService = new GameRpcService(kafkaMessageService);
        RpcProxyHolder.INSTANCE.init(rpcService, gatewayServerConfig.getRpcResponseTopic());
    }

    @Override
    public Server getServer(int serverId) {
        return GateServerManager.getInstance().getServer(serverId);
    }

    @Override
    public String rpcRequestTopic(int serverId) {
        return gatewayServerConfig.getRpcRequestTopic(serverId);
    }
}
