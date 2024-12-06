package com.whk.threadpool.processor;

import lombok.Getter;

@Getter
public enum ProcessorId {

    LOGIN_PROCESSOR(1, "登录处理器"),
    PLAYER_PROCESSOR(2, "玩家处理器"),
    MAP_PROCESSOR(3, "场景处理器"),
    RPC_PROCESSOR(4, "RPC处理器"),


    ;

    private final int id;


    private final String desc;


    ProcessorId(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
