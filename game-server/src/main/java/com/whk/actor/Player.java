package com.whk.actor;

import com.whk.net.channel.GameChannel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
    public String gateInstanceId;

    public boolean isLogin;

    private GameChannel gameChannel;

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
