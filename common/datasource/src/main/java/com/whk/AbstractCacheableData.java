package com.whk;

import io.protostuff.Exclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCacheableData<Entity extends AbstractEntity<ID>, ID> implements CacheableData {
    @Exclude
    private Entity entity;
    @Exclude
    private long updateTime;
    @Exclude
    private boolean isNull = false;

    public abstract ID getId();

}
