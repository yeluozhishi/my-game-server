package com.whk;

public class NullCacheableData extends AbstractCacheableData<NullCacheableData.Null, Long> {
    @Override
    public void updata() {
    }

    @Override
    public Long getId() {
        return 0L;
    }

    static class Null implements IEntity {
    }
}