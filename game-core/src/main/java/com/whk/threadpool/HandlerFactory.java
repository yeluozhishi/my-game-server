package com.whk.threadpool;

import com.whk.threadpool.handler.*;

public enum HandlerFactory {
    INSTANCE;

    public PlayerMessageHandler createPlayerHandler(Object message, long playerId, PlayerMessageRecord record) {
        return new PlayerMessageHandler(message, playerId, record);
    }

    public SceneHandler creatSceneHandler(Runnable runnable) {
        IRecord record = new SceneRecord(runnable);
        return new SceneHandler(record);
    }

}
