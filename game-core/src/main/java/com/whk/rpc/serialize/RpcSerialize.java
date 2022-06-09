package com.whk.rpc.serialize;

import com.whk.rpc.serialize.protostuff.SchemaCache;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.Delegate;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class RpcSerialize {

    public static Objenesis objenesis = new ObjenesisStd(true);

    private static final SchemaCache cachedSchema = SchemaCache.getInstance();

    public static <T> Schema<T> getSchema(Class<T> cls) {
        return (Schema<T>) cachedSchema.get(cls);
    }

    public static  <T> boolean registerDelegate(Delegate<T> delegate) {
        return cachedSchema.registerDelegate(delegate);
    }

    public void serialize(OutputStream output, Object object) throws IOException{
        Class cls = object.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(cls);
            ProtostuffIOUtil.writeTo(output, object, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    protected abstract Object deserialize(InputStream input) throws IOException;
}

