package com.whk.scene.server;

import com.whk.ConfigCacheManager;
import com.whk.SpringUtils;
import script.ScannerClassException;
import com.whk.close.CloseManager;
import com.whk.match.id.UIDUtil;
import com.whk.scene.config.GameServerConfig;
import com.whk.scene.map.SceneManager;
import com.whk.scene.net.RpcSceneProxyHolder;
import com.whk.scene.net.SendMessageHolder;
import com.whk.scene.register.SceneMessageProcessorRegister;
import com.whk.scene.register.SceneTickRegister;
import com.whk.threadpool.ServerType;
import com.whk.threadpool.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import script.ScriptHolder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Service
public class SceneServerBoot {


    private GameServerConfig config;

    private DiscoveryClient discoveryClient;

    private SceneKafkaMessageService kafkaMessageService;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setConfig(GameServerConfig config) {
        this.config = config;
    }

    @Autowired
    public void setKafkaMessageService(SceneKafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    /**
     * 游戏服初始化
     */
    public void init() throws IOException, ScannerClassException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // id生成器
        UIDUtil.init(config.getGameDateConfig().getServer(), config.getGameDateConfig().getZone());
        // 线程池初始化
        ThreadPoolManager.getInstance().initThreadPool(ServerType.SCENE);
        // 消息工具初始化
        kafkaMessageService.init();
        SendMessageHolder.INSTANCE.init(kafkaMessageService);
        // 加载xml
        ConfigCacheManager.INSTANCE.init();
        // rpc
        RpcSceneProxyHolder.init(kafkaMessageService, config);
        // 服务器管理
        SceneServerManager.getInstance().init(config.getGameDateConfig(), discoveryClient);
        // 脚本
        ScriptHolder.INSTANCE.init(config.getGameDateConfig().isDev(), config.getGameDateConfig().getScriptPath());
        // 场景
        SceneManager.INSTANCE.createMainScene();
        // 注册器
        register();
        // 关闭事件注册
        closeRegister();
    }

    /**
     * 注册器
     */
    public void register(){
        // 监听
//        new GameEventRegister();
//        // 循环事件注册
        new SceneTickRegister();
        new SceneMessageProcessorRegister();
    }


    public void closeRegister(){
        CloseManager closeManager = SpringUtils.getBean(CloseManager.class);
        closeManager.add(() -> ThreadPoolManager.getInstance().closeThreadPool());
    }

}
