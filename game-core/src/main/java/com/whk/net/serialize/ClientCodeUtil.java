package com.whk.net.serialize;

import com.whk.rpc.serialize.MessageCodecUtil;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class ClientCodeUtil implements MessageCodecUtil {
    @Override
    public void encode(ByteBuf out, Object message) throws IOException {

    }

    @Override
    public Object decode(byte[] body, Class c) throws IOException {
        return null;
    }
}
