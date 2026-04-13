package com.whk.service;

import com.whk.*;
import com.whk.close.CloseManager;
import com.whk.coder.LengthDecoder;
import com.whk.coder.LengthEncoder;
import com.whk.coder.MessageDecoder;
import com.whk.coder.MessageEncoder;
import com.whk.config.GatewayServerConfig;
import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.match.id.UIDUtil;
import com.whk.net.AuthorizesHandler;
import com.whk.net.GatewayHandler;
import com.whk.net.RpcGateProxyHolder;
import com.whk.net.http.HttpClient;
import com.whk.register.GateMessageProcessorRegister;
import com.whk.register.GateTickRegister;
import com.whk.server.GateServerManager;
import com.whk.threadpool.ServerType;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.processor.ProcessorManager;
import com.whk.tick.WorldTick;
import com.whk.user.UserMgr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import script.ScannerClassException;
import script.ScriptHolder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GatewayServerBoot {

    /**
     * 配置信息
     */
    private GatewayServerConfig config;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private RestTemplate restTemplate;

    private GateKafkaMessageConsumeService kafkaMessageService;

    private DiscoveryClient discoveryClient;

    private DispatchProtocolService dispatchProtocolService;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setKafkaMessageService(GateKafkaMessageConsumeService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Autowired
    public void setConfig(GatewayServerConfig config) {
        config.getGameDateConfig().setPort(config.getGameDateConfig().getPort());
        this.config = config;
    }

    /**
     * 启动netty
     */
    public void startServerNetty() {
        bossGroup = new MultiThreadIoEventLoopGroup(config.getGameDateConfig().getBossThreadCount(), NioIoHandler.newFactory());
        workGroup = new MultiThreadIoEventLoopGroup(config.getGameDateConfig().getWorkThreadCount(), NioIoHandler.newFactory());

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            // 编码器链（出站，从后往前执行）
                            channel.pipeline().addLast(new LengthEncoder());     // 添加总长度 2
                            channel.pipeline().addLast(new MessageEncoder());    // 添加协议号+数据 1

                            channel.pipeline().addLast(new IdleStateHandler(5, 5, 5, TimeUnit.SECONDS));// 0

                            // 解码器链（入站，从前往后执行）
                            channel.pipeline().addLast(new LengthDecoder());     // 去除长度字段 1
                            channel.pipeline().addLast(new MessageDecoder());    // 解析协议号和数据 2
                            channel.pipeline().addLast(new AuthorizesHandler());// 3
                            channel.pipeline().addLast(new GatewayHandler(dispatchProtocolService));// 4
                        }
                    });
            log.info("服务启动，端口：%d".formatted(config.getGameDateConfig().getPort()));
            ChannelFuture future = bootstrap.bind(config.getGameDateConfig().getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            stop();
        }
    }

    /**
     * 关闭
     */
    public void stop() {
        int quietPeriod = 5;
        int timeout = 30;
        TimeUnit timeUnit = TimeUnit.SECONDS;

        workGroup.shutdownGracefully(quietPeriod, timeout, timeUnit);
        bossGroup.shutdownGracefully(quietPeriod, timeout, timeUnit);
    }


    /**
     * 初始化其他配置等
     */
    public void init() throws IOException, ScannerClassException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {

        // id生成器
        UIDUtil.init(config.getGameDateConfig().getServer(), config.getGameDateConfig().getZone());
        // http工具写入
        HttpClient.getInstance().setRestTemplate(restTemplate, config.getEurekaInstanceConfigBean().getInstanceId());
        // 线程池初始化
        ThreadPoolManager.getInstance().initThreadPool(ServerType.GATE);
        // 初始服务器列表
        GateServerManager.getInstance().init(discoveryClient, config);
        // 加载xml
        ConfigLoadManager.init(config.getGameDateConfig().getConfigPath());
        // rpc初始化
        RpcGateProxyHolder.getInstance().init(kafkaMessageService, config);
        // 消息工具初始化
        dispatchProtocolService = new DispatchProtocolService();
        kafkaMessageService.init(dispatchProtocolService);
        // 用户管理初始化
        UserMgr.INSTANCE.init(kafkaMessageService);
        // 脚本载入
        ScriptHolder.INSTANCE.init(config.getGameDateConfig().isDev(), config.getGameDateConfig().getScriptPath());

        // 注册器
        register();

        closeRegister();
    }

    public void closeRegister() {
        CloseManager closeManager = SpringUtils.getBean(CloseManager.class);
        closeManager.add(this::close);
    }

    public void close() {
        WorldTick.INSTANCE.stop();
        ProcessorManager.INSTANCE.stop();
        // 关闭线程池
        ThreadPoolManager.getInstance().closeThreadPool();
        kafkaMessageService.destroy();
    }

    /**
     * 注册器
     */
    public void register() {
        // 定时器
        new GateTickRegister();
        new GateMessageProcessorRegister();
    }
}
