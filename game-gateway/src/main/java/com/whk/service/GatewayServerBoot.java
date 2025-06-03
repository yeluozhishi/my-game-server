package com.whk.service;

import com.whk.ConfigCacheManager;
import com.whk.SpringUtils;
import com.whk.close.CloseManager;
import com.whk.config.GatewayServerConfig;
import com.whk.match.id.UIDUtil;
import com.whk.net.AuthorizesHandler;
import com.whk.net.GatewayHandler;
import com.whk.net.RpcGateProxyHolder;
import com.whk.net.http.HttpClient;
import com.whk.protobuf.message.MessageProto;
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
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
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

    private NioEventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private TransmitOrDispatch transmitOrDispatch;

    private RestTemplate restTemplate;

    private GateKafkaMessageService kafkaMessageService;

    private DiscoveryClient discoveryClient;

    @Autowired
    public void setServerConnector(TransmitOrDispatch transmitOrDispatch) {
        this.transmitOrDispatch = transmitOrDispatch;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setKafkaMessageService(GateKafkaMessageService kafkaMessageService) {
        this.kafkaMessageService = kafkaMessageService;
    }

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    /**
     * 启动netty
     */
    public void startServerNetty() {
        bossGroup = new NioEventLoopGroup(config.getData().getBossThreadCount());
        workGroup = new NioEventLoopGroup(config.getData().getWorkThreadCount());

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            //ChannelOutboundHandlerAdapter在前, ChannelInboundHandlerAdapter在后
                            //channel.pipeline().addLast(new ProtobufEncoder());// 2
                            channel.pipeline().addLast(new ProtobufEncoder());// 1

                            channel.pipeline().addLast(new IdleStateHandler(5, 5, 5, TimeUnit.SECONDS));// 0

                            channel.pipeline().addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));// 1
                            channel.pipeline().addLast(new AuthorizesHandler());// 2
                            channel.pipeline().addLast(new GatewayHandler());// 3
                        }
                    });
            log.info("服务启动，端口：%d".formatted(config.getData().getPort()));
            ChannelFuture future = bootstrap.bind(config.getData().getPort()).sync();
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

    @Autowired
    public void setConfig(GatewayServerConfig config) {
        config.getData().setPort(config.getData().getPort());
        this.config = config;
    }

    /**
     * 初始化其他配置等
     */
    public void init() throws IOException, ScannerClassException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // id生成器
        UIDUtil.init(config.getData().getServer(), config.getData().getZone());
        // http工具写入
        HttpClient.getInstance().setRestTemplate(restTemplate, config.getEurekaInstanceConfigBean().getInstanceId());
        // 线程池初始化
        ThreadPoolManager.getInstance().initThreadPool(ServerType.GATE);
        // 初始化分发器
        transmitOrDispatch.init();
        // 初始服务器列表
        GateServerManager.getInstance().init(discoveryClient, config);
        // 加载xml
        ConfigCacheManager.INSTANCE.init();
        // rpc初始化
        RpcGateProxyHolder.getInstance().init(kafkaMessageService, config);
        // 用户管理初始化
        UserMgr.INSTANCE.init(kafkaMessageService);
        // 脚本载入
        ScriptHolder.INSTANCE.init(config.getData().isDev(), config.getData().getScriptPath());

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
