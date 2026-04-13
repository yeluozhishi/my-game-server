package com.whk.client.service;

import com.whk.coder.LengthDecoder;
import com.whk.coder.LengthEncoder;
import com.whk.coder.MessageDecoder;
import com.whk.coder.MessageEncoder;
import com.whk.client.config.GameClientConfig;
import com.whk.client.net.Gamehandler;
import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.threadpool.ServerType;
import com.whk.threadpool.ThreadPoolManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameClientBoot {

    private GameClientConfig config;

    @Getter
    private Channel channel;

    private DispatchProtocolService dispatchProtocolService;

    public void launch() {
        ThreadPoolManager.getInstance().initThreadPool(ServerType.CLIENT);
        dispatchProtocolService = new DispatchProtocolService();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeOut() * 1000)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        // 编码器链（出站，从后往前执行）
                        channel.pipeline().addLast(new LengthEncoder());     // 添加总长度
                        channel.pipeline().addLast(new MessageEncoder());    // 添加协议号+数据

                        // 解码器链（入站，从前往后执行）
                        channel.pipeline().addLast(new LengthDecoder());     // 去除长度字段
                        channel.pipeline().addLast(new MessageDecoder());    // 解析协议号和数据
                        channel.pipeline().addLast(new Gamehandler(dispatchProtocolService));
                    }
                });

        ChannelFuture future = bootstrap.connect(config.getDefaultGameGatewayHost(), config.getDefaultGameGatewayPort());
        channel = future.channel();
        future.addListener((ChannelFutureListener) e -> {
            if (future.isSuccess()) {
                log.info("连接:" + config.getDefaultGameGatewayHost() + ":" + config.getDefaultGameGatewayPort() + "成功");
            } else {
                log.info("连接失败：" + future.cause());
            }
        });

    }

    @Autowired
    public void setConfig(GameClientConfig config) {
        this.config = config;
    }
}
