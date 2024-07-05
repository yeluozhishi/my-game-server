package com.whk.net.rpc.serialize;

import com.whk.net.rpc.serialize.RpcSerialize;
import com.whk.net.rpc.serialize.Serialize;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class RpcSerializeFactory extends BasePooledObjectFactory<Serialize> {
    @Override
    public Serialize create() {
        return new RpcSerialize();
    }

    @Override
    public PooledObject<Serialize> wrap(Serialize obj) {
        return new DefaultPooledObject<>(obj);
    }
}
