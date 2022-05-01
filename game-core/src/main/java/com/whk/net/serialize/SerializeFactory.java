package com.whk.net.serialize;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class SerializeFactory extends BasePooledObjectFactory<Serialize> {
    @Override
    public Serialize create() {
        return new Serialize();
    }

    @Override
    public PooledObject<Serialize> wrap(Serialize obj) {
        return new DefaultPooledObject<>(obj);
    }
}
