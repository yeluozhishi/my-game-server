package com.whk.net.rpc.serialize.protostuff;

import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import com.whk.net.rpc.serialize.Serialize;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;

import java.io.InputStream;

public class ProtostuffSerialize extends Serialize {

    @Override
    public <T> T deserialize(InputStream input, Class<T> cls) {
        try {
            Object message = cls.getDeclaredConstructor().newInstance();
            Schema<Object> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(input, message, schema);
            return (T) message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}

