package com.whk.actor;

import com.whk.actor.component.*;
import com.whk.actor.component.attribute.Attributes;
import io.protostuff.Exclude;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Actor implements Serializable {
    private long id = 0;
    // 属性
    private Attributes attributes = new Attributes();
    // 移动对象
    @Exclude
    private Movement movement = new Movement();
    // 行为
    @Exclude
    private Behavior behavior = new Behavior();
    // 变动属性
    private Statuses statuses = new Statuses();

}
