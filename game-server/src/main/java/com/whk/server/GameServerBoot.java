package com.whk.server;

import com.whk.LoadXml;
import com.whk.config.KafkaConfig;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.SendMessageHolder;
import com.whk.rpc.consumer.GameRpcService;
import com.whk.server.GameServerManager;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
    @PostConstruct
    public void init() {
        SendMessageHolder.INSTANCE.init(kafkaTemplate);
        // 加载xml
        LoadXml.getInstance().loadAll();
        // 服务器管理
        gameServerManager = new GameServerManager(config, discoveryClient);
        // rpc
        var rpcWorkerGroup = new DefaultEventExecutorGroup(2);
        var rpcService = new GameRpcService(rpcWorkerGroup, kafkaTemplate);
        RpcGameProxyHolder.init(gameServerManager, rpcService, eurekaInstanceConfigBean.getInstanceId());
    }

     public  int value() {
        return 1;
    }
}
