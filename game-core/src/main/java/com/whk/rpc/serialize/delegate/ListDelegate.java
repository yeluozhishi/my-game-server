package com.whk.rpc.serialize.delegate;

import io.protostuff.*;
import io.protostuff.runtime.Delegate;
import io.protostuff.runtime.RuntimeSchema;

import java.io.IOException;
import java.util.List;

public class ListDelegate<T> implements Delegate<List<T>> {

    private final Schema<List> LIST_SCHEMA = RuntimeSchema.getSchema(List.class);

    @Override
    public WireFormat.FieldType getFieldType() {
        return WireFormat.FieldType.BYTES;
    }

    @Override
    public List<T> readFrom(Input input) throws IOException {
        var wrapper = LIST_SCHEMA.newMessage();
        ProtostuffIOUtil.mergeFrom(input.readByteArray(), wrapper, LIST_SCHEMA);
        return wrapper;
    }

    @Override
    public void writeTo(Output output, int i, List<T> ts, boolean b) throws IOException {
        var serialBytes = ProtostuffIOUtil.toByteArray(ts, LIST_SCHEMA, LinkedBuffer.allocate(512));
        output.writeByteArray(i, serialBytes, b);
    }

    @Override
    public void transfer(Pipe pipe, Input input, Output output, int i, boolean b) throws IOException {
        output.writeByteArray(i, input.readByteArray(), b);
    }

    @Override
    public Class<?> typeClass() {
        return List.class;
    }
}
