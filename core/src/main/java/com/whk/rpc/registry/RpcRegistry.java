package com.whk.rpc.registry;

import com.whk.rpc.serialize.protostuff.ProtostuffCodecUtil;
import com.whk.rpc.serialize.protostuff.ProtostuffDecoder;
import com.whk.rpc.serialize.protostuff.ProtostuffEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.*;

public class RpcRegistry {  
    private int port;  
    public RpcRegistry(int port){  
        this.port = port;  
    }  
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ProtostuffCodecUtil util = new ProtostuffCodecUtil();
            util.setRpcDirect(true);
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            		.channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProtostuffEncoder(util));
                            ch.pipeline().addLast(new ProtostuffDecoder(util));
                            ch.pipeline().addLast(new RegistryHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)       
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
            ChannelFuture future = b.bind(port).sync();      
            System.out.println("GP RPC Registry start listen at " + port );
            future.channel().closeFuture().sync();    
        } catch (Exception e) {  
             bossGroup.shutdownGracefully();    
             workerGroup.shutdownGracefully();  
        }  
    }
    
    
    public static void main(String[] args) throws Exception {    
        ExecutorService service = Executors.newCachedThreadPool();
//        for (int i = 8080; i < 8090; i++) {
//            int finalI = i;
            service.execute(() -> new RpcRegistry(8080).start());
//        }
    }    
}  
