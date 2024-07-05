package com.whk.net.rpc.serialize.protostuff;

import com.whk.net.rpc.serialize.Serialize;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ProtostuffSerializeFactory extends BasePooledObjectFactory<Serialize> {

    @Override
    public Serialize create() {
        return createProtostuff();
    }

    @Override
    public PooledObject<Serialize> wrap(Serialize protostuff) {
        return new DefaultPooledObject<>(protostuff);
    }

    private Serialize createProtostuff() {
        return new ProtostuffSerialize();
    }
}
