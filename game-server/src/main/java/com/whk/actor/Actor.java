package com.whk.actor;

import com.whk.actor.component.*;
import com.whk.actor.component.attribute.Attributes;
import io.protostuff.Exclude;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Actor implements Serializable {
    @Exclude
    private long id = 0;
    @Exclude
    private Attributes attributes = new Attributes();
    @Exclude
    private Movement movement = new Movement();
    @Exclude
    private Behavior behavior = new Behavior();
    @Exclude
    private Statuses statuses = new Statuses();

}
