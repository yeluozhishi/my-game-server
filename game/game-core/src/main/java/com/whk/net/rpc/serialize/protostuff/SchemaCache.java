package com.whk.net.rpc.serialize.protostuff;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.protostuff.Schema;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.Delegate;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeSchema;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SchemaCache {

    private final static DefaultIdStrategy idStrategy = ((DefaultIdStrategy) RuntimeEnv.ID_STRATEGY);

    private static class SchemaCacheHolder {
        private static final SchemaCache cache = new SchemaCache();
    }

    public static SchemaCache getInstance() {
        return SchemaCacheHolder.cache;
    }

    public <T> boolean registerDelegate(Delegate<T> delegate){
        return idStrategy.registerDelegate(delegate);
    }

    private final Cache<Class<?>, Schema<?>> cache = CacheBuilder.newBuilder()
            .maximumSize(1024).expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    private Schema<?> get(final Class<?> cls, Cache<Class<?>, Schema<?>> cache) {
        try {
            return cache.get(cls, (Callable<RuntimeSchema<?>>) () -> RuntimeSchema.createFrom(cls, idStrategy));
        } catch (ExecutionException e) {
            return null;
        }
    }

    public Schema<?> get(final Class<?> cls) {
        return get(cls, cache);
    }
}

