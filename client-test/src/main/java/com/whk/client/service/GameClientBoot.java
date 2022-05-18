package com.whk.client.service;

import com.whk.client.config.GameClientConfig;
import com.whk.client.model.User;
import com.whk.net.game.Gamehandler;
import com.whk.net.serialize.CodeUtil;
import com.whk.rpc.consumer.MessageSendHandler;
import com.whk.rpc.serialize.protostuff.ProtostuffCodecUtil;
import com.whk.rpc.serialize.protostuff.ProtostuffDecoder;
import com.whk.rpc.serialize.protostuff.ProtostuffEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class GameClientBoot {

    private GameClientConfig config;

    private Bootstrap bootstrap;

    private EventLoopGroup eventLoopGroup;

    private Logger logger = Logger.getLogger(GameClientBoot.class.getName());

    private Channel channel;

    public void launch(){
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeOut() * 1000)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        CodeUtil util = new CodeUtil();
                        util.setRpcDirect(false);
                        channel.pipeline().addLast(new ProtostuffEncoder(util));
                        channel.pipeline().addLast(new ProtostuffDecoder(util));
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
