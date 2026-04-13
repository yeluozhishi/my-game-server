package com.whk.coder;

import com.google.protobuf.ByteString;
import com.whk.MessageWrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageDecoder extends SimpleChannelInboundHandler<ByteBuf> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex();
        int cmd = in.readInt();

        int remainLength = in.readableBytes();
        if (remainLength > 0) {
            byte[] bytes = new byte[remainLength];
            in.readBytes(bytes);
            MessageWrap messageWrap = new MessageWrap(cmd, ByteString.copyFrom(bytes));
            ctx.fireChannelRead(messageWrap);
        }
    }

}
