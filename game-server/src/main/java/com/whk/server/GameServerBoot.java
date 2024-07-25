package com.whk.server;

import com.whk.ConfigCacheManager;
import com.whk.actor.PlayerMgr;
import com.whk.config.GameServerConfig;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.SendMessageHolder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.threadpool.ServerType;
import com.whk.threadpool.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import script.ScriptHolder;


@Service
public class GameServerBoot {

    private GameServerConfig config;

    private DiscoveryClient discoveryClient;

    private KafkaMessageService kafkaMessageService;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setConfig(GameServerConfig config) {
        this.config = config;
    }

    @Autowired
    public void setKafkaMessageService(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    /**
     * 游戏服初始化
     */
    public void init() {
        // 线程池初始化
        ThreadPoolManager.getInstance().initThreadPool(ServerType.GAME);
        // 消息工具初始化
        SendMessageHolder.INSTANCE.init(kafkaMessageService);
        kafkaMessageService.init();
        // 加载xml
        ConfigCacheManager.INSTANCE.init();
        // rpc
        var rpcService = new GameRpcService(ThreadPoolManager.getInstance().getRpcThread(), kafkaMessageService);
        RpcGameProxyHolder.init(rpcService, config);
        // 服务器管理
        GameServerManager.getInstance().init(config.getGameDateConfig().getZone(), discoveryClient);

        PlayerMgr.INSTANCE.init();

        ScriptHolder.INSTANCE.init(config.getGameDateConfig().isDev(), config.getGameDateConfig().getArtifactId(),
                "common/%s".formatted(config.getGameDateConfig().getScriptArtifactId()));
    }
}
