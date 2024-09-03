package com.whk.threadpool;

import com.whk.listener.eventlistener.IEvent;
import com.whk.threadpool.handler.*;

public enum HandlerFactory {
    INSTANCE;

    public PlayerMessageHandler createPlayerHandler(Object message, long playerId, IHandler record) {
        return new PlayerMessageHandler(message, playerId, record);
    }

    public DbHandler creatDBHandler(Runnable futureTask) {
        IHandler record = new DbRecord(futureTask);
        return new DbHandler(record);
    }

    public SceneHandler creatSceneHandler(Runnable runnable) {
        IHandler record = new SceneRecord(runnable);
        return new SceneHandler(record);
    }

    public <T extends IEvent> AbstractHandler createEventHandler(T event, IHandler record) {
        switch (record.threadType()) {
            case PLAYER_THREAD -> {
                return new PlayerMessageHandler(event, event.getOrderId(), record);
            }

            case SCENE_THREAD -> {
                return new SceneHandler(event, record);
            }
            default -> throw new UnsupportedOperationException("不支持的EventHandler类型");
        }

    }
}
