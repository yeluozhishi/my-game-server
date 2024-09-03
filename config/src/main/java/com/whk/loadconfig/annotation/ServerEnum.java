package com.whk.loadconfig.annotation;

import lombok.Getter;

/**
 * @author Administrator
 */

@Getter
public enum ServerEnum {
    /**
     * 普通游戏服类型
     */
    GAME_TYPE(0),
    /**
     * 提审服
     */
    TRIAL_TYPE(1),
    /**
     * 跨服类型
     */
    CROSS_TYPE(2),
    /**
     * 中控
     */
    WEB_TYPE(3),
    /**
     * 测试服，其实就是游戏服，仅用于区别正常游戏服
     */
    TEST_GAME_TYPE(4),
    /**
     * 网关服
     */
    GATE_TYPE(5),
    /**
     * 世界服
     */
    WORLD_TYPE(6);

    private final int id;

    ServerEnum(int id){
        this.id = id;
    }

}
