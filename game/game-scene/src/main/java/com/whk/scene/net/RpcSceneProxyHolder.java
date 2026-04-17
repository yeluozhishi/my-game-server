package com.whk.scene.net;

import com.whk.message.Server;
import com.whk.net.kafka.KafkaMessageConsumeService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.net.rpc.proxy.RpcServerProxy;
import com.whk.scene.config.GameServerConfig;
import com.whk.scene.server.SceneServerManager;

public class RpcSceneProxyHolder extends RpcServerProxy {

    private GameServerConfig gameServerConfig;

    private static final RpcSceneProxyHolder INSTANCE = new RpcSceneProxyHolder();

    private RpcSceneProxyHolder() {
    }

    public static RpcSceneProxyHolder getInstance() {
        return INSTANCE;
    }

    public void init(KafkaMessageConsumeService kafkaMessageConsumeService, GameServerConfig config) {
        gameServerConfig = config;
        var rpcService = new GameRpcService(kafkaMessageConsumeService);
        RpcProxyHolder.INSTANCE.init(rpcService, gameServerConfig.getRpcResponseTopic());
    }


    @Override
    public Server getServer(long serverId) {
        return SceneServerManager.getInstance().getServer(serverId);
    }

    @Override
    public String rpcRequestTopic(long serverId) {
        return gameServerConfig.getRpcRequestTopic(serverId);
    }
}
