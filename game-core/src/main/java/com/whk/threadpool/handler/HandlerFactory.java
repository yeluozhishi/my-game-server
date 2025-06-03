package com.whk.threadpool.handler;

import com.whk.dispatchprotocol.PlayerMessageRecord;
import com.whk.net.rpc.model.MessageRequest;

public enum HandlerFactory {
    INSTANCE;

    public PlayerMessageHandler createPlayerHandler(Object message, long playerId, PlayerMessageRecord record) {
        return new PlayerMessageHandler(message, playerId, record);
    }

    public PlayerMessageHandler createUserHandler(Object message, long userId, PlayerMessageRecord record) {
        return new PlayerMessageHandler(message, userId, record);
    }

    public DbHandler createDbHandler(String orderId, Runnable futureTask) {
        return new DbHandler(orderId, futureTask);
    }


    public RPCMessageHandler creatRPCHandler(MessageRequest request) {
        return new RPCMessageHandler(request);
    }
}
