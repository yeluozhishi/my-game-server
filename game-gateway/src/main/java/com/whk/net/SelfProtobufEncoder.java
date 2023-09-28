package com.whk.net;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/28
 */
public class SelfProtobufEncoder extends MessageToByteEncoder<MessageLiteOrBuilder> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, ByteBuf out) throws Exception {
        if (msg instanceof MessageLite) {
            var body = ((MessageLite) msg).toByteArray();
            var commond = 101;
            var lenght = body.length;
            out.writeInt(commond);
            out.writeInt(lenght);
            out.writeBytes(body);


        } else if (msg instanceof MessageLite.Builder) {
            wrappedBuffer(((MessageLite.Builder) msg).build().toByteArray());
        }

    }
}
