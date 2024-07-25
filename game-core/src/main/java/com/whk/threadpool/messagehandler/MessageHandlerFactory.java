package com.whk.threadpool.messagehandler;

import com.whk.threadpool.dispatchprotocol.MessageHandlerRecord;

public enum MessageHandlerFactory {
    INSTANCE;

    public PlayerMessageHandler createPlayerEvent(Object message, long playerId, MessageHandlerRecord record) {
        return new PlayerMessageHandler(message, playerId, record);
    }

    public UserMessageHandler createUserEvent(Object message, long userId, MessageHandlerRecord record) {
        return new UserMessageHandler(message, userId, record);
    }
}
