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

    public abstract void save(byte[] data);

    public IEntity updateEntity() {
        save(MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(this).array());
        return entity;
    }
}
