package com.whk.net.serialize;

import com.whk.net.Message;
import com.whk.rpc.serialize.RpcSerialize;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;

import java.io.InputStream;

public class Serialize extends RpcSerialize {

    @Override
    public Object deserialize(InputStream input) {
        try {
            Class<Message> cls = Message.class;
            Message message = objenesis.newInstance(cls);
            Schema<Message> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(input, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
