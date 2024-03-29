package com.whk.rpc.serialize.protostuff;

import com.google.common.io.Closer;
import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.serialize.MessageCodecUtil;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProtostuffCodecUtil implements MessageCodecUtil {
    private final ThreadLocal<Closer> closer = new ThreadLocal<>();
    private final ProtostuffSerializePool pool =
            ProtostuffSerializePool.getProtostuffPoolInstance(new ProtostuffSerializeFactory());
    private boolean rpcDirect = false;

    public boolean isRpcDirect() {
        return rpcDirect;
    }

    public void setRpcDirect(boolean rpcDirect) {
        this.rpcDirect = rpcDirect;
    }

    private Closer getCloser() {
        Closer c = closer.get();
        if (c == null) {
            c = Closer.create();
            closer.set(c);
        }
        return c;
    }

    @Override
    public void encode(final ByteBuf out, final Object message) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getCloser().register(byteArrayOutputStream);
            ProtostuffSerialize protostuffSerialization = (ProtostuffSerialize)pool.borrow();
            protostuffSerialization.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            pool.restore(protostuffSerialization);
        } finally {
            getCloser().close();
        }
    }

    @Override
    public Object decode(byte[] body, Class c) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            getCloser().register(byteArrayInputStream);
            ProtostuffSerialize protostuffSerialization = (ProtostuffSerialize)pool.borrow();
            protostuffSerialization.setRpcDirect(rpcDirect);
            Object obj = protostuffSerialization.deserialize(byteArrayInputStream, MessageRequest.class);
            pool.restore(protostuffSerialization);
            return obj;
        } finally {
            getCloser().close();
        }
    }
}

