package com.whk.service;

import com.whk.config.GatewayServerConfig;
import com.whk.http.HttpClient;
import com.whk.net.GatewayHandler;
import com.whk.net.serialize.CodeUtil;
import com.whk.rpc.serialize.protostuff.ProtostuffDecoder;
import com.whk.rpc.serialize.protostuff.ProtostuffEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    public void setServerConnector(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
                            CodeUtil util = new CodeUtil();
                            channel.pipeline().addLast(new ProtostuffEncoder(util));
                            channel.pipeline().addLast(new ProtostuffDecoder(util));
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
        HttpClient.setRestTemplate(restTemplate);
        serverConnector.initServerManager();

    }
}
