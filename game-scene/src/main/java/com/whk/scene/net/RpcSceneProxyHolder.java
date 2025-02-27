package com.whk.scene.net;

import com.whk.message.Server;
import com.whk.net.kafka.KafkaMessageService;
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

    public void init(KafkaMessageService kafkaMessageService, GameServerConfig config) {
        gameServerConfig = config;
        var rpcService = new GameRpcService(kafkaMessageService);
        RpcProxyHolder.INSTANCE.init(rpcService, gameServerConfig.getRpcResponseTopic());
    }


    @Override
    public Server getServer(int serverId) {
        return SceneServerManager.getInstance().getServer(serverId);
    }

    @Override
    public String rpcRequestTopic(int serverId) {
        return gameServerConfig.getRpcRequestTopic(serverId);
    }
}
