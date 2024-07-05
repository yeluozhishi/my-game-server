package com.whk.net.rpc.serialize;

import com.whk.net.rpc.serialize.protostuff.SchemaCache;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Protostuff 不支持Map、List等ImmutableCollections序列化或反序列化，需要包装类。
 * 出现UnsupportedOperationException ImmutableCollections.uoe 异常
 * 请使用对应包装类
 */
public abstract class Serialize {

    public static Objenesis objenesis = new ObjenesisStd(true);

    private static final SchemaCache CACHED_SCHEMA = SchemaCache.getInstance();

    public static Schema<Object> getSchema(Class<?> cls) {
        return (Schema<Object>) CACHED_SCHEMA.get(cls);
    }

    public void serialize(OutputStream output, Object object) throws IOException {
        Class<?> cls = object.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<Object> schema = getSchema(cls);
            ProtostuffIOUtil.writeTo(output, object, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    protected abstract <T> T deserialize(InputStream input, Class<T> cls) throws IOException;
}

