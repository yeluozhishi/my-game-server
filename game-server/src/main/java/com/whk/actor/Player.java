package com.whk.actor;

import com.whk.net.channel.GameChannel;
import org.springframework.data.annotation.Transient;

/**
 * @author Administrator
 */
public class Player extends Actor{
    /**
     * 角色名
     */
    public String useName;
    /**
     * 角色分类
     */
    public int career;
    /**
     * 性别
     */
    public int sex;

    /**
     * 网关id
     */
    @Transient
    public String gateInstanceId;

    @Transient
    public boolean isLogin = false;

    @Transient
    private GameChannel gameChannel;

    public Player(Long id) {
        super(id);
    }

    public Player(Long id, GameChannel gameChannel, String gateInstanceId, Boolean isLogin) {
        super(id);
        this.gateInstanceId = gateInstanceId;
        this.isLogin = isLogin;
        this.gameChannel = gameChannel;
    }

    @Override
    public void init() {

    }

    public String getGateInstanceId() {
        return gateInstanceId;
    }
}
