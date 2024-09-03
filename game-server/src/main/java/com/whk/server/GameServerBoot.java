package com.whk.server;

import com.whk.ConfigCacheManager;
import com.whk.SpringUtils;
import com.whk.actor.PlayerMgr;
import com.whk.close.CloseManager;
import com.whk.config.GameServerConfig;
import com.whk.eventlistener.GameEventRegister;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.SendMessageHolder;
import com.whk.scene.SceneManager;
import com.whk.schedule.ScheduleEvent;
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

    private GameKafkaMessageService kafkaMessageService;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setConfig(GameServerConfig config) {
        this.config = config;
    }

    @Autowired
    public void setKafkaMessageService(GameKafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    /**
     * 游戏服初始化
     */
    public void init() {
        // 线程池初始化
        ThreadPoolManager.getInstance().initThreadPool(ServerType.GAME);
        // 消息工具初始化
        kafkaMessageService.init();
        SendMessageHolder.INSTANCE.init(kafkaMessageService);
        // 加载xml
        ConfigCacheManager.INSTANCE.init();
        // rpc
        RpcGameProxyHolder.init(kafkaMessageService, config);
        // 服务器管理
        GameServerManager.getInstance().init(config.getGameDateConfig().getZone(), discoveryClient);
        // 玩家管理
        PlayerMgr.INSTANCE.init();
        // 脚本
        ScriptHolder.INSTANCE.init(config.getGameDateConfig().isDev(), config.getGameDateConfig().getArtifactId(),
                "common/%s".formatted(config.getGameDateConfig().getScriptArtifactId()));
        // 监听
        GameEventRegister.registerListener();
        // 场景
        SceneManager.INSTANCE.createMainScene();
        // 循环事件
        ScheduleEvent.INSTANCE.initScheduleEvent();
        // 关闭事件注册
        closeRegister();
    }

    public void closeRegister(){
        CloseManager closeManager = SpringUtils.getBean(CloseManager.class);
        closeManager.add(() -> ThreadPoolManager.getInstance().closeThreadPool());
    }
}
