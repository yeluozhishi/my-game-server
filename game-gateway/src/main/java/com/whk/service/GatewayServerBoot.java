package com.whk.service;

import com.whk.ConfigCacheManager;
import com.whk.config.GatewayServerConfig;
import com.whk.net.*;
import com.whk.net.http.HttpClient;
import com.whk.net.rpc.consumer.GameRpcService;
import com.whk.schedule.OnceDelayTask;
import com.whk.server.GateServerManager;
import com.whk.threadpool.ServerType;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.user.UserMgr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.whk.protobuf.message.MessageProto;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class GatewayServerBoot {

    /**
     * 配置信息
     */
    private GatewayServerConfig config;

    private NioEventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private final Logger logger = Logger.getLogger(GatewayServerBoot.class.getName());

    private TransmitAndDispatch transmitAndDispatch;

    private RestTemplate restTemplate;

    private GateKafkaMessageService kafkaMessageService;

    private DiscoveryClient discoveryClient;

    private OnceDelayTask onceDelayTask;

    @Autowired
    public void setServerConnector(TransmitAndDispatch transmitAndDispatch) {
        this.transmitAndDispatch = transmitAndDispatch;
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
    public void startServerNetty(){
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
            logger.info("服务启动，端口：%d".formatted(config.getData().getPort()));
            ChannelFuture future = bootstrap.bind(config.getData().getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.severe(e.getMessage());
            stop();
        }
    }

    /**
     * 关闭
     */
    public void stop(){
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
    public void init() {
        // http工具写入
        HttpClient.getInstance().setRestTemplate(restTemplate, config.getEurekaInstanceConfigBean().getInstanceId());
        // 线程池初始化
        ThreadPoolManager.getInstance().initThreadPool(ServerType.GATE);
        // 初始化分发器
        transmitAndDispatch.init();
        // 初始服务器列表
        GateServerManager.getInstance().init(discoveryClient, config.getData().getZone());
        // 加载xml
        ConfigCacheManager.INSTANCE.init();
        // rpc初始化
        var rpcService = new GameRpcService(ThreadPoolManager.getInstance().getRpcThread(), kafkaMessageService);
        RpcGateProxyHolder.init(rpcService, config.getTopic());
        // 用户管理初始化
        UserMgr.INSTANCE.init(kafkaMessageService);

        onceDelayTask = new OnceDelayTask();
        onceDelayTask.run();
    }
}
