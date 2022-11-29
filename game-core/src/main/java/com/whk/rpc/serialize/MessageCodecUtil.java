package com.whk.rpc.serialize;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface MessageCodecUtil {

    int MESSAGE_LENGTH = 4;

    /**
     * 编码
     * @param out 输出
     * @param message 消息
     * @throws IOException
     */
    void encode(final ByteBuf out, final Object message) throws IOException;

    /**
     * 解码
     * @param body 消息体
     * @param c 类
     * @return
     * @throws IOException
     */
    Object decode(byte[] body, Class c) throws IOException;
}
