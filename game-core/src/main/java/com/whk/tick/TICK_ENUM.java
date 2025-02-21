package com.whk.tick;

import lombok.Getter;

@Getter
public enum TICK_ENUM {

    // 服务器100millis事件
    SERVER_HEART_100_MILLIS(100),
    // 服务器500millis事件
    SERVER_HEART_500_MILLIS(500),
    // 服务器1s事件
    SERVER_HEART_1_S(1000),
    // 服务器1m事件
    SERVER_HEART_1_M(1000 * 60),
    // 服务器5m事件
    SERVER_HEART_5_M(1000 * 60 * 5),
    // 服务器10m事件
    SERVER_HEART_10_M(1000 * 60 * 10),
    // 服务器1h事件
    SERVER_HEART_1_H(1000 * 60 * 60),
    ;

    private final long diff;

    TICK_ENUM(long diff) {
        this.diff = diff;
    }
}
