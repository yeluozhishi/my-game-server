package com.whk.net.serialize;

import com.google.common.io.Closer;
import com.whk.rpc.serialize.MessageCodecUtil;
import com.whk.rpc.serialize.protostuff.ProtostuffSerializePool;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CodeUtil implements MessageCodecUtil {
    private final ThreadLocal<Closer> closer = new ThreadLocal<>();
    private final ProtostuffSerializePool pool = ProtostuffSerializePool.getProtostuffPoolInstance(new SerializeFactory());

    private Closer getCloser() {
        Closer c = closer.get();
        if (c == null) {
            c = Closer.create();
            closer.set(c);
        }
        return c;
    }

    @Override
    public void encode(ByteBuf out, Object message) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getCloser().register(byteArrayOutputStream);
            Serialize serialize = (Serialize)pool.borrow();
            serialize.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            pool.restore(serialize);
        } finally {
            getCloser().close();
        }
    }

    @Override
    public Object decode(byte[] body) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            getCloser().register(byteArrayInputStream);
            Serialize serialize = (Serialize)pool.borrow();
            Object obj = serialize.deserialize(byteArrayInputStream);
            pool.restore(serialize);
            return obj;
        } finally {
            getCloser().close();
        }
    }
}
