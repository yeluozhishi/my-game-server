package com.whk.coder;

import com.google.protobuf.Message;
import com.whk.CmdToMessageUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {

    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        byte[] content = msg.toByteArray();
        // 协议号
        out.writeInt(CmdToMessageUtil.getInstance().getCmd(msg.getClass()));
        out.writeBytes(content);
    }

}
