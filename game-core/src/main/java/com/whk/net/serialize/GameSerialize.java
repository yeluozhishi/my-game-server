package com.whk.net.serialize;

import com.whk.net.enity.Message;
import com.whk.rpc.serialize.Serialize;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;

import java.io.InputStream;

public class GameSerialize extends Serialize {

    @Override
    public Object deserialize(InputStream input, Class c) {
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
