package com.whk.net.rpc.serialize;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import com.whk.protobuf.message.MessageProto;

import java.io.IOException;

public interface MessageCodecUtil {

    int MESSAGE_LENGTH = 4;

    /**
     * 编码
     * @param out 输出
     * @param message 消息
     */
    void encode(final ByteBuf out, final Object message);

    /**
     * 解码
     * @param body 消息体
     */
    <T> T decode(byte[] body) throws InvalidProtocolBufferException;
}
