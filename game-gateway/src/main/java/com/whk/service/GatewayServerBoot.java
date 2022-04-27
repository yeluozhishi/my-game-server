package com.whk.service;

import com.whk.config.GatewayServerConfig;
import com.whk.net.gate.GatewayHandler;
import com.whk.net.serialize.CodeUtil;
import com.whk.rpc.serialize.protostuff.ProtostuffCodecUtil;
import com.whk.rpc.serialize.protostuff.ProtostuffDecoder;
import com.whk.rpc.serialize.protostuff.ProtostuffEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 启动netty
     */
    public void startServer(){
        bossGroup = new NioEventLoopGroup(config.getBossThreadCount());
        workGroup = new NioEventLoopGroup(config.getWorkThreadCount());

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            CodeUtil util = new CodeUtil();
                            util.setRpcDirect(true);
                            channel.pipeline().addLast(new ProtostuffEncoder(util));
                            channel.pipeline().addLast(new ProtostuffDecoder(util));
                            channel.pipeline().addLast(new GatewayHandler());
                        }
                    });
            logger.info("服务启动，端口：" + config.getPort());
            ChannelFuture future = bootstrap.bind(config.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        config.setPort(config.getPort());
        this.config = config;
    }
}
