package com.whk.threadpool;

import com.whk.dispatchprotocol.PlayerMessageHandler;
import com.whk.dispatchprotocol.UserMessageHandler;
import com.whk.threadpool.db.DbHandler;
import com.whk.threadpool.db.DbRecord;
import com.whk.threadpool.handler.InstanceHandlerInterface;

public enum HandlerFactory {
    INSTANCE;

    public PlayerMessageHandler createPlayerHandler(Object message, long playerId, InstanceHandlerInterface record) {
        return new PlayerMessageHandler(message, playerId, record);
    }

    public UserMessageHandler createUserHandler(Object message, long userId, InstanceHandlerInterface record) {
        return new UserMessageHandler(message, userId, record);
    }

    public DbHandler creatDBHandler(Runnable futureTask) {
        InstanceHandlerInterface record = new DbRecord(ThreadPoolManager.getInstance().getExecutor(TheadType.DB_THREAD), futureTask);
        return new DbHandler(record);
    }
}
