package com.whk.service;

import com.whk.LoadXml;
import com.whk.config.GatewayServerConfig;
import com.whk.net.GameChannelIdleStateHandler;
import com.whk.net.GatewayHandler;
import com.whk.net.MessageHandler;
import com.whk.net.RpcGateProxyHolder;
import com.whk.net.concurrent.GameEventExecutorGroup;
import com.whk.net.http.HttpClient;
import com.whk.rpc.consumer.GameRpcService;
import com.whk.user.UserMgr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.whk.protobuf.message.MessageOuterClass;

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

    private ServerConnector serverConnector;

    private RestTemplate restTemplate;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    public void setServerConnector(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 启动netty
     */
    public void startServer(){
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
                            channel.pipeline().addLast(new ProtobufEncoder());
                            channel.pipeline().addLast(new ProtobufDecoder(MessageOuterClass.Message.getDefaultInstance()));
                            channel.pipeline().addLast(new GatewayHandler());
                        }
                    });
            logger.info("服务启动，端口：" + config.getData().getPort());
            ChannelFuture future = bootstrap.bind(config.getData().getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        HttpClient.setRestTemplate(restTemplate, config.getInstanceId());
        // 初始化服务器
        serverConnector.init(config);
        // 加载xml
        LoadXml.getInstance().loadAll();
        // rpc初始化
        var workerGroup = new GameEventExecutorGroup(config.getData().getWorkThreadCount());
        var rpcWorkerGroup = new DefaultEventExecutorGroup(2);
        var rpcService = new GameRpcService(rpcWorkerGroup, kafkaTemplate);
        RpcGateProxyHolder.init(serverConnector.getServerManager(), rpcService, config.getInstanceId());
        // 用户管理初始化
        UserMgr.INSTANCE.init(kafkaTemplate, workerGroup, config, (gameChannel) -> {
            // 初始化GameChannel
            gameChannel.getPipeline().addLast(new GameChannelIdleStateHandler(300, 300, 300));
            gameChannel.getPipeline().addLast(new MessageHandler());
        });
    }
}
