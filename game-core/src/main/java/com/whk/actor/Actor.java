package com.whk.actor;

import com.whk.actor.attribute.Attributes;
import io.protostuff.Exclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class Actor implements Serializable {
    private long id = 0L;
    // 属性
    private Attributes attributes = new Attributes();
    // 行为
    @Exclude
    private Behavior behavior = new Behavior();
    // 变动属性
    private Statuses statuses = new Statuses();

}
