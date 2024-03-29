package com.whk.net.serialize;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class SerializeFactory extends BasePooledObjectFactory<GameSerialize> {
    @Override
    public GameSerialize create() {
        return new GameSerialize();
    }

    @Override
    public PooledObject<GameSerialize> wrap(GameSerialize obj) {
        return new DefaultPooledObject<>(obj);
    }
}
