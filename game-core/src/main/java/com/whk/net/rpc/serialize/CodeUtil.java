package com.whk.net.rpc.serialize;

import com.google.common.io.Closer;
import com.google.protobuf.InvalidProtocolBufferException;
import com.whk.net.rpc.serialize.protostuff.ProtostuffSerializePool;
import com.whk.protobuf.message.MessageWrapperProto;
import io.netty.buffer.ByteBuf;
import com.whk.protobuf.message.MessageProto;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

public class CodeUtil implements MessageCodecUtil {
    private final ThreadLocal<Closer> closer = new ThreadLocal<>();

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
    public void encode(ByteBuf out, Object message) {

    }

    @Override
    public <T> T decode(byte[] body) throws InvalidProtocolBufferException {
        return (T) MessageWrapperProto.MessageWrapper.parseFrom(body);
    }


    public <T> Optional<T> decodeRpc(byte[] body, Class<T> c) throws IOException {
        //直接使用byte[]包装为ByteBuf，减少一次数据复制
        ByteBuf byteBuf = Unpooled.wrappedBuffer(body);
        if (byteBuf.readableBytes() < MessageCodecUtil.MESSAGE_LENGTH) {
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
        getCloser().register(byteArrayInputStream);
        RpcSerialize rpcSerialize = (RpcSerialize)poolRpc.borrow();
        try {
            T obj = rpcSerialize.deserialize(byteArrayInputStream, c);
            poolRpc.restore(rpcSerialize);
            return Optional.ofNullable(obj);
        } finally {
            getCloser().close();
        }
    }

    public ByteBuf encodeRpc(Object message) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            getCloser().register(byteArrayOutputStream);
            RpcSerialize rpcSerialize = (RpcSerialize)poolRpc.borrow();
            rpcSerialize.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            var out = Unpooled.buffer();
            out.writeInt(dataLength);
            out.writeBytes(body);
            poolRpc.restore(rpcSerialize);
            return out;
        } finally {
            getCloser().close();
        }
    }

}
