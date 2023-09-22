package com.whk.actor;

import org.springframework.data.annotation.Id;

public abstract class Actor {
    /**
     * 角色id
     */
    @Id
    public final Long id;
    /**
     * 是否死亡
     */
    public Boolean isDead = false;

    public Actor(Long id) {
        this.id = id;
        init();
    }

    /**
     * 初始化其他数据
     */
    public abstract void init();
}
