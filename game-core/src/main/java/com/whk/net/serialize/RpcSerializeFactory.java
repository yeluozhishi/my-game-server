package com.whk.net.serialize;

import com.whk.net.RPC.RpcSerialize;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class RpcSerializeFactory extends BasePooledObjectFactory<RpcSerialize> {
    @Override
    public RpcSerialize create() {
        return new RpcSerialize();
    }

    @Override
    public PooledObject<RpcSerialize> wrap(RpcSerialize obj) {
        return new DefaultPooledObject<>(obj);
    }
}
