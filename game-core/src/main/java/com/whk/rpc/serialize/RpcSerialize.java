package com.whk.rpc.serialize;

import com.whk.rpc.serialize.Serialize;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;

import java.io.IOException;
import java.io.InputStream;

public class RpcSerialize extends Serialize {

    @Override
    public Object deserialize(InputStream input, Class cls) {
        try {
            Object message = objenesis.newInstance(cls);
            Schema<Object> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(input, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
