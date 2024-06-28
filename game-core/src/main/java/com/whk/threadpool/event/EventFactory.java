package com.whk.threadpool.event;

import com.whk.threadpool.dispatchprotocol.MessageHandlerRecord;

public enum EventFactory {
    INSTANCE;

    public PlayerEventHandler createPlayerEvent(Object message, long playerId, MessageHandlerRecord record) {
        return new PlayerEventHandler(message, playerId, record);
    }

    public UserEventHandler createUserEvent(Object message, long userId, MessageHandlerRecord record) {
        return new UserEventHandler(message, userId, record);
    }
}
