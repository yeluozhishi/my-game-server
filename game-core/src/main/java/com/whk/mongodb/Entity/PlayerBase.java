package com.whk.mongodb.Entity;

public class PlayerBase {

    public String id;

    // 职业
    public int kind;

    public int sex;

    public Long lastLogin;

    public PlayerBase(String id, int kind, int sex, Long lastLogin) {
        this.id = id;
        this.kind = kind;
        this.sex = sex;
        this.lastLogin = lastLogin;
    }
}
