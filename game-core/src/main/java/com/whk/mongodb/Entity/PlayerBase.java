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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }
}
