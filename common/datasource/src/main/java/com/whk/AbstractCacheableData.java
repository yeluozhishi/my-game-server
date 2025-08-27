package com.whk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractCacheableData<Entity extends IEntity, ID> implements CacheableData {
    private Entity entity;

    private long updateTime;

    private boolean isNull;

    public abstract void updata();

    public abstract ID getId();

}
