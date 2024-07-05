package com.whk.net.rpc.serialize.protostuff;

import com.whk.net.rpc.Constats.RpcSystemConfig;
import com.whk.net.rpc.serialize.Serialize;
import lombok.Getter;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

@Getter
public class ProtostuffSerializePool {
    private final GenericObjectPool<Serialize> protostuffPool;

    public static ProtostuffSerializePool getProtostuffPoolInstance(BasePooledObjectFactory<Serialize> pooledObjectFactory) {
        return new ProtostuffSerializePool(RpcSystemConfig.SERIALIZE_POOL_MAX_TOTAL, RpcSystemConfig.SERIALIZE_POOL_MIN_IDLE,
                RpcSystemConfig.SERIALIZE_POOL_MAX_WAIT_MILLIS, RpcSystemConfig.SERIALIZE_POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS, pooledObjectFactory);
    }

    public ProtostuffSerializePool(final int maxTotal, final int minIdle, final long maxWaitMillis,
                                   final long minEvictableIdleTimeMillis, BasePooledObjectFactory<Serialize> pooledObjectFactory) {
        protostuffPool = new GenericObjectPool<>(pooledObjectFactory);

        GenericObjectPoolConfig<Serialize> config = new GenericObjectPoolConfig<>();

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

}

