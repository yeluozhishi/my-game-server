package com.whk.client.service;

import com.whk.client.config.GameClientConfig;
import com.whk.client.net.Gamehandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whk.protobuf.message.MessageOuterClass;

import java.util.logging.Logger;

@Component
public class GameClientBoot {

    private GameClientConfig config;

    private final Logger logger = Logger.getLogger(GameClientBoot.class.getName());

    private Channel channel;

    public void launch(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeOut() * 1000)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline().addLast(new ProtobufEncoder());
                        channel.pipeline().addLast(new ProtobufDecoder(MessageOuterClass.Message.getDefaultInstance()));
                        channel.pipeline().addLast(new Gamehandler());
                    }
                });

        ChannelFuture future = bootstrap.connect(config.getDefaultGameGatewayHost(), config.getDefaultGameGatewayPort());
        channel = future.channel();
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (future.isSuccess()){
                logger.info("连接:" + config.getDefaultGameGatewayHost() + ":" + config.getDefaultGameGatewayPort() + "成功");
            } else {
                logger.info("连接失败：" + future.cause());
            }
        });

    }

    public Channel getChannel() {
        return channel;
    }

    @Autowired
    public void setConfig(GameClientConfig config) {
        this.config = config;
    }
}
