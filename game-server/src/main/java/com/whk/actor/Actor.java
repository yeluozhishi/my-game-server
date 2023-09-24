package com.whk.actor;

import lombok.Data;

@Data
public abstract class Actor {
    /**
     * 角色id
     */
    public final Long id;
    /**
     * 是否死亡
     */
    public Boolean isDead = false;

    public Actor(){
        id = 0L;
    }

    public Actor(Long id) {
        this.id = id;
        init();
    }

    /**
     * 初始化其他数据
     */
    public abstract void init();
}
