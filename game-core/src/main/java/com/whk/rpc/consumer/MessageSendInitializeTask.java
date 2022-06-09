package com.whk.rpc.consumer;

import com.whk.rpc.Constats.RpcSystemConfig;
import com.whk.rpc.serialize.RpcSerializeProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 与服务器建立连接任务
 */
public class MessageSendInitializeTask implements Callable<Boolean> {

    private final Logger logger = Logger.getLogger(MessageSendInitializeTask.class.getName());

    private final RpcServerLoader loader;
    private final EventLoopGroup eventLoopGroup;
    private final InetSocketAddress serverAddress;
    private final RpcSerializeProtocol protocol;
    private final String remark;

    MessageSendInitializeTask(RpcServerLoader loader, EventLoopGroup eventLoopGroup, InetSocketAddress serverAddress,
                              RpcSerializeProtocol protocol, String remark) {
        this.loader = loader;
        this.eventLoopGroup = eventLoopGroup;
        this.serverAddress = serverAddress;
        this.protocol = protocol;
        this.remark = remark;
    }

    @Override
    public Boolean call() {
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(serverAddress);
        b.handler(new MessageSendChannelInitializer().buildRpcSerializeProtocol(protocol));

        ChannelFuture channelFuture = b.connect();
        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            if (channelFuture1.isSuccess()) {
                // 与服务器建立连接后设置句柄
                MessageSendHandler handler = channelFuture1.channel().pipeline().get(MessageSendHandler.class);
                handler.setConnected(true);
                handler.setEventLoopGroup(eventLoopGroup);
                handler.setSerializeProtocol(protocol);
                loader.setMessageSendHandler(serverAddress, handler);
                logger.warning(String.format("NettyRPC Client connected !!! address:%s, protocol:%s, remark:%s",
                        serverAddress, protocol.toString(), remark));
            } else {
                eventLoopGroup.schedule(() -> {
                    logger.warning("Remote NettyRPC server is down, start reconnecting to: "
                            + serverAddress.getAddress().getHostAddress() + ':' + serverAddress.getPort()
                            + ", remark:" + remark);
                    MessageSendInitializeTask.this.call();
                }, RpcSystemConfig.SYSTEM_PROPERTY_CLIENT_RECONNECT_DELAY, TimeUnit.SECONDS);
            }
        });
        return Boolean.TRUE;
    }
}
