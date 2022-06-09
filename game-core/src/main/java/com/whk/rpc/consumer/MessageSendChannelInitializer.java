package com.whk.rpc.consumer;

import com.whk.rpc.serialize.RpcSerializeProtocol;
import com.whk.rpc.serialize.protostuff.ProtostuffCodecUtil;
import com.whk.rpc.serialize.protostuff.ProtostuffDecoder;
import com.whk.rpc.serialize.protostuff.ProtostuffEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 加密解密统一配置
 */
public class MessageSendChannelInitializer extends ChannelInitializer<SocketChannel> {

    private RpcSerializeProtocol protocol;

    public MessageSendChannelInitializer buildRpcSerializeProtocol(RpcSerializeProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        switch (protocol) {
            case JDKSERIALIZE:
//                handler.getInstance(JdkNativeSendHandler.class).handle(pipeline);
                break;

            case KRYOSERIALIZE:
//                handler.getInstance(KryoSendHandler.class).handle(pipeline);
                break;

            case HESSIANSERIALIZE:
//                handler.getInstance(HessianSendHandler.class).handle(pipeline);
                break;

            case PROTOSTUFFSERIALIZE:
                ProtostuffCodecUtil util = new ProtostuffCodecUtil();
                util.setRpcDirect(false);
                pipeline.addLast(new ProtostuffEncoder(util));
                pipeline.addLast(new ProtostuffDecoder(util));
                pipeline.addLast(new MessageSendHandler());
                break;
            default: throw new NullPointerException("not init RpcSerializeProtocol");
        }
    }
}
