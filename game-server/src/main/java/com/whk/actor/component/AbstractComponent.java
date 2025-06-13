package com.whk.actor.component;

import com.whk.net.kafka.MessageInnerCoder;
import io.protostuff.Exclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractComponent<IEntity> {
    @Exclude
    private IEntity entity;

    @Exclude
    private long updateTime;

    public abstract void updata(byte[] data);

    public abstract long getId();

    public void updateEntity() {
        updata(MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(this).array());
    }
}
