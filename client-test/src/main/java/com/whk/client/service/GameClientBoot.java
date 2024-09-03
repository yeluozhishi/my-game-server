package com.whk.client.service;

import com.whk.client.config.GameClientConfig;
import com.whk.client.net.Gamehandler;
import com.whk.threadpool.ServerType;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.dispatchprotocol.DispatchProtocolService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.whk.protobuf.message.MessageProto;

import java.util.logging.Logger;

@Component
public class GameClientBoot {

    private GameClientConfig config;

    private final Logger logger = Logger.getLogger(GameClientBoot.class.getName());

    @Getter
    private Channel channel;

    private DispatchProtocolService dispatchProtocolService;

    public void launch(){
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
                        channel.pipeline().addLast(new ProtobufEncoder());

                        channel.pipeline().addLast(new ProtobufDecoder(MessageProto.Message.getDefaultInstance()));
                        channel.pipeline().addLast(new Gamehandler(dispatchProtocolService));
                    }
                });

        ChannelFuture future = bootstrap.connect(config.getDefaultGameGatewayHost(), config.getDefaultGameGatewayPort());
        channel = future.channel();
        future.addListener((ChannelFutureListener) _ -> {
            if (future.isSuccess()){
                logger.info("连接:" + config.getDefaultGameGatewayHost() + ":" + config.getDefaultGameGatewayPort() + "成功");
            } else {
                logger.info("连接失败：" + future.cause());
            }
        });

    }

    @Autowired
    public void setConfig(GameClientConfig config) {
        this.config = config;
    }
}
