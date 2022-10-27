package com.whk.actor;

public abstract class Actor {
    /**
     * 角色id
     */
    public final String id;
    /**
     * 是否死亡
     */
    public Boolean isDead = false;

    public Actor(String id) {
        this.id = id;
    }
}
