package com.whk.threadpool;

import com.whk.dispatchprotocol.PlayerMessageHandler;
import com.whk.listener.eventlistener.event.IEvent;
import com.whk.threadpool.db.DbHandler;
import com.whk.threadpool.db.DbRecord;
import com.whk.threadpool.handler.AbstractHandler;
import com.whk.threadpool.handler.HandlerInterface;

public enum HandlerFactory {
    INSTANCE;

    public PlayerMessageHandler createPlayerHandler(Object message, long playerId, HandlerInterface record) {
        return new PlayerMessageHandler(message, playerId, record);
    }

    public DbHandler creatDBHandler(Runnable futureTask) {
        HandlerInterface record = new DbRecord(ThreadPoolManager.getInstance().getExecutor(TheadType.DB_THREAD), futureTask);
        return new DbHandler(record);
    }

    public <T extends IEvent> AbstractHandler createEventHandler(T event, HandlerInterface record) {
        switch (event.getTheadType()) {
            case PLAYER_THREAD, SCENE_THREAD -> {
                return new PlayerMessageHandler(event, event.getOrderId(), record);
            }
            default -> throw new UnsupportedOperationException("不支持的EventHandler类型");
        }

    }
}
