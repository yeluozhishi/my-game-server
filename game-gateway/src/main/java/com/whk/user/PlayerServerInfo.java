package com.whk.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerServerInfo {


    /**
     * 游戏服id
     */
    private int serverId;

    /**
     * 跳转游戏服id
     */
    private int toServerId;


    public PlayerServerInfo(int serverId, int toServerId) {
        this.serverId = serverId;
        this.toServerId = toServerId;
    }
}
