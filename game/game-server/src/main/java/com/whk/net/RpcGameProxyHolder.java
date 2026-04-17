package com.whk.net;

import com.whk.config.GameServerConfig;
import com.whk.message.Server;
import com.whk.net.kafka.KafkaMessageConsumeService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.net.rpc.proxy.RpcServerProxy;
import com.whk.server.GameServerManager;

public class RpcGameProxyHolder extends RpcServerProxy {

    private GameServerConfig gameServerConfig;

    private static final RpcGameProxyHolder INSTANCE = new RpcGameProxyHolder();

    private RpcGameProxyHolder() {
    }

    public static RpcGameProxyHolder getInstance() {
        return INSTANCE;
    }

    public void init(KafkaMessageConsumeService kafkaMessageConsumeService, GameServerConfig config) {
        gameServerConfig = config;
        var rpcService = new GameRpcService(kafkaMessageConsumeService);
        RpcProxyHolder.INSTANCE.init(rpcService, gameServerConfig.getRpcResponseTopic());
    }

    @Override
    public Server getServer(long serverId) {
        return GameServerManager.getInstance().getServer(serverId);
    }

    @Override
    public String rpcRequestTopic(long serverId) {
        return gameServerConfig.getRpcRequestTopic(serverId);
    }

}
