package com.whk.net.serialize;

import com.google.common.io.Closer;
import com.whk.rpc.serialize.RpcSerialize;
import com.whk.rpc.serialize.MessageCodecUtil;
import com.whk.rpc.serialize.protostuff.ProtostuffSerializePool;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CodeUtil implements MessageCodecUtil {
    private final ThreadLocal<Closer> closer = new ThreadLocal<>();
    private final ProtostuffSerializePool pool = ProtostuffSerializePool.getProtostuffPoolInstance(new SerializeFactory());

    private final ProtostuffSerializePool poolRpc = ProtostuffSerializePool.getProtostuffPoolInstance(new RpcSerializeFactory());

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
            GameSerialize gameSerialize = (GameSerialize)pool.borrow();
            gameSerialize.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            pool.restore(gameSerialize);
        } finally {
            getCloser().close();
        }
    }

    @Override
    public Object decode(byte[] body, Class c) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            getCloser().register(byteArrayInputStream);
            GameSerialize gameSerialize = (GameSerialize)pool.borrow();
            Object obj = gameSerialize.deserialize(byteArrayInputStream, c);
            pool.restore(gameSerialize);
            return obj;
        } finally {
            getCloser().close();
        }
    }


    public Object decodeRpc(byte[] body, Class c) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            getCloser().register(byteArrayInputStream);
            RpcSerialize rpcSerialize = (RpcSerialize)poolRpc.borrow();
            Object obj = rpcSerialize.deserialize(byteArrayInputStream, c);
            poolRpc.restore(rpcSerialize);
            return obj;
        } finally {
            getCloser().close();
        }
    }

    public void encodeRpc(ByteBuf out, Object message) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getCloser().register(byteArrayOutputStream);
            RpcSerialize rpcSerialize = (RpcSerialize)poolRpc.borrow();
            rpcSerialize.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            poolRpc.restore(rpcSerialize);
        } finally {
            getCloser().close();
        }
    }

}
