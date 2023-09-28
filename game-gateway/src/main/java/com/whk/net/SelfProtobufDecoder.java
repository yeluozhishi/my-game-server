package com.whk.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/28
 */
public class SelfProtobufDecoder<ByteBuf> extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, io.netty.buffer.ByteBuf in, List<Object> out) throws Exception {

    }
}
