package com.whk.net.rpc.serialize;

import com.google.common.io.Closer;
import com.whk.net.rpc.serialize.protostuff.ProtostuffSerializeFactory;
import com.whk.net.rpc.serialize.protostuff.ProtostuffSerializePool;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

public class ProtostuffSerializeUtil implements MessageCodecUtil {

    private final ProtostuffSerializePool poolRpc = ProtostuffSerializePool.getProtostuffPoolInstance(new ProtostuffSerializeFactory());

    public <T> Optional<T> decode(byte[] body, Class<T> c) {
        //直接使用byte[]包装为ByteBuf，减少一次数据复制
        ByteBuf byteBuf = Unpooled.wrappedBuffer(body);
        if (byteBuf.readableBytes() < MESSAGE_LENGTH) {
            return Optional.empty();
        }

        byteBuf.markReaderIndex();
        int messageLength = byteBuf.readInt();

        if (messageLength < 0) {
            return Optional.empty();
        }
        if (byteBuf.readableBytes() < messageLength) {
            byteBuf.resetReaderIndex();
            return Optional.empty();
        }
        byte[] messageBody = new byte[messageLength];
        byteBuf.readBytes(messageBody);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(messageBody);
        Serialize rpcSerialize = poolRpc.borrow();
        T obj = rpcSerialize.deserialize(byteArrayInputStream, c);
        poolRpc.restore(rpcSerialize);
        return Optional.ofNullable(obj);
    }

    public ByteBuf encode(Object message) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Serialize rpcSerialize = poolRpc.borrow();
        rpcSerialize.serialize(byteArrayOutputStream, message);
        byte[] body = byteArrayOutputStream.toByteArray();
        int dataLength = body.length;
        var out = Unpooled.buffer();
        out.writeInt(dataLength);
        out.writeBytes(body);
        poolRpc.restore(rpcSerialize);
        return out;
    }

}
