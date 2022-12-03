package com.whk.actor;

import com.whk.net.channel.GameChannel;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Administrator
 */
@Document(collection = "Player")
public class Player extends Actor{
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

    public boolean isLogin = false;

    private GameChannel gameChannel;

    public Player(String id, GameChannel gameChannel) {
        super(id);
    }

    public Player(String id, GameChannel gameChannel, Boolean isLogin) {
        super(id);
        this.isLogin = isLogin;
        this.gameChannel = gameChannel;
    }

    @Override
    public void init() {

    }
}
