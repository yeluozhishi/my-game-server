package com.whk.service;

import com.whk.LoadXml;
import com.whk.config.KafkaConfig;
import com.whk.messageholder.SendMessageHolder;
import com.whk.rpc.consumer.GameRpcService;
import com.whk.rpc.consumer.proxy.RpcProxyHolder;
import com.whk.server.GameServerManager;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameServerBoot {

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private KafkaConfig config;

    /**
     * 服务器管理
     */
    private GameServerManager gameServerManager;

    private DiscoveryClient discoveryClient;

    private EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @Autowired
    public void setEurekaInstanceConfigBean(EurekaInstanceConfigBean eurekaInstanceConfigBean) {
        this.eurekaInstanceConfigBean = eurekaInstanceConfigBean;
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setConfig(KafkaConfig config) {
        this.config = config;
    }

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    /**
     * 游戏服初始化
     */
    public void init() {
        SendMessageHolder.INSTANCE.setKafkaTemplate(kafkaTemplate);
        // 加载xml
        LoadXml.getInstance().loadAll();
        // 服务器管理
        gameServerManager = new GameServerManager(config, discoveryClient, eurekaInstanceConfigBean.getInstanceId());
        // rpc
        var rpcWorkerGroup = new DefaultEventExecutorGroup(2);
        var rpcService = new GameRpcService(rpcWorkerGroup);
        RpcProxyHolder.INSTANCE.init(rpcService, config.getServer(), kafkaTemplate);
    }
}
