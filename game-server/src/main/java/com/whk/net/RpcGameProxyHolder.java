package com.whk.net;

import com.whk.config.GameServerConfig;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.server.GameKafkaMessageService;
import com.whk.server.GameServerManager;
import com.whk.threadpool.ThreadPoolManager;

public class RpcGameProxyHolder {

    private static GameServerConfig gameServerConfig;

    public static void init(KafkaMessageService kafkaMessageService, GameServerConfig config) {
        gameServerConfig = config;
        var rpcService = new GameRpcService(kafkaMessageService);
        RpcProxyHolder.INSTANCE.init(rpcService, gameServerConfig.getRpcResponseTopic());
    }

    public static <T extends IRpcService> T getInstance(Class<T> clazz, int serverId) {
        var server = GameServerManager.getInstance().getServer(serverId);
        return server.map(value -> (T) RpcProxyHolder.INSTANCE.getInstance(clazz, gameServerConfig.getRpcRequestTopic(value.getId())))
                .orElse(null);
    }
}
