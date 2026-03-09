package com.whk;

public class NullCacheableData extends AbstractCacheableData<NullCacheableData.NullEntity, Long> {

    public static final NullCacheableData INSTANCE = new NullCacheableData();


    @Override
    public void update() {
    }

    @Override
    public Long getId() {
        return 0L;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    static class NullEntity implements IEntity {
    }
}