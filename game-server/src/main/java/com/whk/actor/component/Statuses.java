package com.whk.actor.component;

import lombok.Data;

@Data
public class Statuses {
    private volatile long hp;

    private volatile long mp;

    private volatile boolean death;


}
