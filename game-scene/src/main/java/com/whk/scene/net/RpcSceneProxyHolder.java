package com.whk.scene.net;

import com.whk.actor.PlayerActor;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.scene.config.GameServerConfig;
import com.whk.scene.server.SceneServerManager;

public class RpcSceneProxyHolder {

    private static GameServerConfig gameServerConfig;

    public static void init(KafkaMessageService kafkaMessageService, GameServerConfig config) {
        gameServerConfig = config;
        var rpcService = new GameRpcService(kafkaMessageService);
        RpcProxyHolder.INSTANCE.init(rpcService, gameServerConfig.getRpcResponseTopic());
    }


    public static <T extends IRpcService> T getInstance(Class<T> clazz, int serverId) {
        var server = SceneServerManager.getInstance().getServer(serverId);
        return server.map(value -> (T) RpcProxyHolder.INSTANCE.getInstance(clazz, gameServerConfig.getRpcRequestTopic(value.getId()), String.valueOf(serverId)))
                .orElse(null);
    }
}
