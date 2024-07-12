package com.whk.server;

import com.whk.ConfigCacheManager;
import com.whk.LoadXml;
import com.whk.actor.PlayerMgr;
import com.whk.config.GameDateConfig;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.SendMessageHolder;
import com.whk.net.kafka.KafkaMessageService;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.threadpool.ServerType;
import com.whk.threadpool.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GameServerBoot {

    private GameDateConfig config;

    private DiscoveryClient discoveryClient;

    private EurekaInstanceConfigBean eurekaInstanceConfigBean;

    private KafkaMessageService kafkaMessageService;

    @Autowired
    public void setEurekaInstanceConfigBean(EurekaInstanceConfigBean eurekaInstanceConfigBean) {
        this.eurekaInstanceConfigBean = eurekaInstanceConfigBean;
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setConfig(GameDateConfig config) {
        this.config = config;
    }

    @Autowired
    public void setKafkaMessageService(KafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    /**
     * 游戏服初始化
     */
    @PostConstruct
    public void init() {
        // 线程池初始化
        ThreadPoolManager.getInstance().initThreadPool(ServerType.GATE);
        // 消息工具初始化
        SendMessageHolder.INSTANCE.init(kafkaMessageService);
        // 加载xml
        ConfigCacheManager.INSTANCE.init();
        // 服务器管理
        GameServerManager.getInstance().init(config.getZone(), discoveryClient);
        // rpc
        var rpcService = new GameRpcService(ThreadPoolManager.getInstance().getRpcThread(), kafkaMessageService);
        RpcGameProxyHolder.init(rpcService, config.getZone());
        PlayerMgr.INSTANCE.init();
    }
}
