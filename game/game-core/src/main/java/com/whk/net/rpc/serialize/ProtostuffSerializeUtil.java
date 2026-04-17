package com.whk.net.rpc.serialize;

import com.whk.net.rpc.serialize.protostuff.ProtostuffSerializeFactory;
import com.whk.net.rpc.serialize.protostuff.ProtostuffSerializePool;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ProtostuffSerializeUtil implements MessageCodecUtil {

    private final ProtostuffSerializePool poolRpc = ProtostuffSerializePool.getProtostuffPoolInstance(new ProtostuffSerializeFactory());

    public <T> T decode(byte[] body, Class<T> c) {
        //直接使用byte[]包装为ByteBuf，减少一次数据复制
        ByteBuf byteBuf = Unpooled.wrappedBuffer(body);
        if (byteBuf.readableBytes() < MESSAGE_LENGTH) {
            throw new IllegalArgumentException("数据包长度小于" + MESSAGE_LENGTH);
        }

        byteBuf.markReaderIndex();
        int messageLength = byteBuf.readInt();

        if (messageLength < 0) {
            throw new IllegalArgumentException("数据包长度小于0");
        }
        if (byteBuf.readableBytes() < messageLength) {
            byteBuf.resetReaderIndex();
            throw new IllegalArgumentException("数据包长度与实际不符");
        }
        byte[] messageBody = new byte[messageLength];
        byteBuf.readBytes(messageBody);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(messageBody);
        Serialize rpcSerialize = poolRpc.borrow();
        T obj = rpcSerialize.deserialize(byteArrayInputStream, c);
        poolRpc.restore(rpcSerialize);
        return obj;
    }

    public <T> ByteBuf encode(T message) {
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
