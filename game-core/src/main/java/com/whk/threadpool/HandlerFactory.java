package com.whk.threadpool;

import com.whk.net.rpc.model.MessageRequest;
import com.whk.threadpool.handler.*;

public enum HandlerFactory {
    INSTANCE;

    public PlayerMessageHandler createPlayerHandler(Object message, long playerId, PlayerMessageRecord record) {
        return new PlayerMessageHandler(message, playerId, record);
    }

    public SceneEventHandler creatSceneHandler(Runnable runnable) {
        IRecord record = new SceneRecord(runnable);
        return new SceneEventHandler(record);
    }

    public DbHandler createDbHandler(String orderId, Runnable futureTask){
        return new DbHandler(orderId, futureTask);
    }


    public RPCMessageHandler creatRPCHandler(MessageRequest request, Runnable runnable) {
        return new RPCMessageHandler(request.getOrderId(), request.getProcessorId(), runnable);
    }
}
