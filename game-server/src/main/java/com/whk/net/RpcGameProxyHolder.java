package com.whk.net;

import com.whk.actor.Player;
import com.whk.config.GameServerConfig;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.server.GameServerManager;

public class RpcGameProxyHolder {

    private static GameServerConfig gameServerConfig;

    public static void init(KafkaMessageService kafkaMessageService, GameServerConfig config) {
        gameServerConfig = config;
        var rpcService = new GameRpcService(kafkaMessageService);
        RpcProxyHolder.INSTANCE.init(rpcService, gameServerConfig.getRpcResponseTopic());
    }

    public static <T extends IRpcService> T getInstance(Class<T> clazz, int serverId) {
        var server = GameServerManager.getInstance().getServer(serverId);
        return server.map(value -> (T) RpcProxyHolder.INSTANCE.getInstance(clazz, gameServerConfig.getRpcRequestTopic(value.getId()), String.valueOf(serverId)))
                .orElse(null);
    }

    public static <T extends IRpcService> T getInstance(Class<T> clazz, Player player) {
        var server = GameServerManager.getInstance().getServer(player.getServerInfo().getPresentServerId());
        return server.map(value -> (T) RpcProxyHolder.INSTANCE.getInstance(clazz, gameServerConfig.getRpcRequestTopic(value.getId()), String.valueOf(player.getId())))
                .orElse(null);
    }
}
