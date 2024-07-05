package com.whk.actor;

import com.whk.actor.component.*;
import io.protostuff.Tag;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Actor implements Serializable {
    @Tag(1)
    private long id = 0;
    @Tag(2)
    private BasicInfo basicInfo = new BasicInfo();
    @Tag(3)
    private Attributes attributes = new Attributes();
    @Tag(4)
    private Movement movement = new Movement();
    @Tag(5)
    private Behavior behavior = new Behavior();
    @Tag(6)
    private Statuses statuses = new Statuses();

    /**
     * 初始化其他数据
     */
    public abstract void init();
}
