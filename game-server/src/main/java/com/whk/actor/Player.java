package com.whk.actor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
public class Player extends Actor{
    /**
     * 角色名
     */
    private String useName;
    /**
     * 角色分类
     */
    private int career;
    /**
     * 性别
     */
    private int sex;

    /**
     * 网关id
     */
    private String gateInstanceId;

    private boolean isLogin;


    public Player(Long id, String gateInstanceId, Boolean isLogin) {
        super(id);
        this.gateInstanceId = gateInstanceId;
        this.isLogin = isLogin;
    }

    @Override
    public void init() {

    }

}
