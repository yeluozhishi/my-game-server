package com.whk.net.rpc.serialize;

import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;

import java.io.InputStream;

public class RpcSerialize extends Serialize {

    @Override
    public <T> T deserialize(InputStream input, Class<T> cls) {
        try {
            T message = objenesis.newInstance(cls);
            Schema<Object> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(input, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
