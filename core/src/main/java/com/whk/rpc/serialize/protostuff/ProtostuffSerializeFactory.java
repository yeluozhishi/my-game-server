package com.whk.rpc.serialize.protostuff;

import com.whk.rpc.serialize.RpcSerialize;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ProtostuffSerializeFactory extends BasePooledObjectFactory<RpcSerialize> {

    @Override
    public RpcSerialize create() {
        return createProtostuff();
    }

    @Override
    public PooledObject<RpcSerialize> wrap(RpcSerialize protostuff) {
        return new DefaultPooledObject<>(protostuff);
    }

    private RpcSerialize createProtostuff() {
        return new ProtostuffSerialize();
    }
}
