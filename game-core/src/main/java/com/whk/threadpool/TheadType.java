package com.whk.threadpool;

import java.util.concurrent.ThreadPoolExecutor;

public enum TheadType {
    DB_THREAD,
    PLAYER_THREAD,
    SCENE_THREAD,
    ;

    public ThreadPoolExecutor getExecutor(){
        return ThreadPoolManager.getInstance().getExecutor(this);
    }
}
