package com.whk.net.rpc.serialize;

import com.whk.net.rpc.serialize.protostuff.SchemaCache;
import com.whk.net.rpc.serialize.wrapper.SerializeDeserializeWrapper;
import com.whk.net.rpc.serialize.wrapper.WrapperSet;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Protostuff 不支持Map、List等ImmutableCollections序列化或反序列化，需要包装类。
 * 出现UnsupportedOperationException ImmutableCollections.uoe 异常
 * 请使用对应包装类
 */
@Slf4j
public class Serialize {

    public <T> void serialize(OutputStream output, T object) {
        Class<T> cls = (Class<T>) object.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = SchemaCache.getInstance().get(cls);
            if (WrapperSet.getInstance().isWrapper(cls)) {
                SerializeDeserializeWrapper<T> wrapper = SerializeDeserializeWrapper.builder(object);
                var wrapperSchema = SchemaCache.getInstance().get(SerializeDeserializeWrapper.class);
                ProtostuffIOUtil.writeTo(output, wrapper, wrapperSchema, buffer);
            } else {
                ProtostuffIOUtil.writeTo(output, object, schema, buffer);
            }
        } catch (IOException e) {
            log.error("serialize error", e);
        } finally {
            buffer.clear();
        }
    }

    protected <T> T deserialize(InputStream input, Class<T> cls) {
        try {
            if (WrapperSet.getInstance().isWrapper(cls)) {
                SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<>();
                var wrapperSchema = SchemaCache.getInstance().get(SerializeDeserializeWrapper.class);
                ProtostuffIOUtil.mergeFrom(input, wrapper, wrapperSchema);
                return wrapper.getData();
            } else {
                T message = cls.getDeclaredConstructor().newInstance();
                Schema<T> schema = SchemaCache.getInstance().get(cls);
                ProtostuffIOUtil.mergeFrom(input, message, schema);
                return message;
            }
        } catch (Exception e) {
            log.error("serialize error", e);
        }
        return null;
    }
}

