package com.whk.rpc.serialize.protostuff;

import com.whk.rpc.serialize.Serialize;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import static com.whk.rpc.Constats.RpcSystemConfig.*;

public class ProtostuffSerializePool {
    private GenericObjectPool<Serialize> protostuffPool;

    public static ProtostuffSerializePool getProtostuffPoolInstance(BasePooledObjectFactory pooledObjectFactory) {
        return new ProtostuffSerializePool(SERIALIZE_POOL_MAX_TOTAL, SERIALIZE_POOL_MIN_IDLE,
                SERIALIZE_POOL_MAX_WAIT_MILLIS, SERIALIZE_POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS, pooledObjectFactory);
    }

    public ProtostuffSerializePool(final int maxTotal, final int minIdle, final long maxWaitMillis,
                                   final long minEvictableIdleTimeMillis, BasePooledObjectFactory pooledObjectFactory) {
        protostuffPool = new GenericObjectPool<>(pooledObjectFactory);

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        protostuffPool.setConfig(config);
    }

    public Serialize borrow() {
        try {
            return getProtostuffPool().borrowObject();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void restore(final Serialize object) {
        getProtostuffPool().returnObject(object);
    }

    public GenericObjectPool<Serialize> getProtostuffPool() {
        return protostuffPool;
    }
}

