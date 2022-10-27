package com.whk.actor;

/**
 * @author Administrator
 */
public class Player extends Actor{
    public Player(String id) {
        super(id);
    }

    /**
     * 角色名
     */
    public String useName;
    /**
     * 角色分类
     */
    public int kind;
    /**
     * 性别
     */
    public int sex;

}
