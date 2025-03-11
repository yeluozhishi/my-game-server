package com.whk.server;

import com.whk.ConfigCacheManager;
import com.whk.SpringUtils;
import com.whk.actor.PlayerMgr;
import com.whk.threadpool.processor.ProcessorManager;
import com.whk.tick.WorldTick;
import script.ScannerClassException;
import com.whk.close.CloseManager;
import com.whk.config.GameServerConfig;
import com.whk.eventlistener.GameEventRegister;
import com.whk.match.id.UIDUtil;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.SendMessageHolder;
import com.whk.register.GameMessageProcessorRegister;
import com.whk.register.GameTickRegister;
import com.whk.threadpool.ServerType;
import com.whk.threadpool.ThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import script.ScriptHolder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


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
    public void init() throws IOException, ScannerClassException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // id生成器
        UIDUtil.init(config.getGameDateConfig().getServer(), config.getGameDateConfig().getZone());
        // 线程池初始化
        ThreadPoolManager.getInstance().initThreadPool(ServerType.GAME);
        // 消息工具初始化
        kafkaMessageService.init();
        SendMessageHolder.INSTANCE.init(kafkaMessageService);
        // 加载xml
        ConfigCacheManager.INSTANCE.init();
        // rpc
        RpcGameProxyHolder.getInstance().init(kafkaMessageService, config);
        // 服务器管理
        GameServerManager.getInstance().init(config.getGameDateConfig(), discoveryClient);
        // 玩家管理
        PlayerMgr.INSTANCE.init();
        // 脚本
        ScriptHolder.INSTANCE.init(config.getGameDateConfig().isDev(), config.getGameDateConfig().getScriptPath());
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
        new GameEventRegister();
        // 循环事件注册
        new GameTickRegister();
        new GameMessageProcessorRegister();
    }


    public void closeRegister(){
        CloseManager closeManager = SpringUtils.getBean(CloseManager.class);
        closeManager.add(this::close);
    }

    public void close(){
        WorldTick.INSTANCE.stop();
        ProcessorManager.INSTANCE.stop();
        ThreadPoolManager.getInstance().closeThreadPool();
    }
}
