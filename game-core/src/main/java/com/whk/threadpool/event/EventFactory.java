package com.whk.threadpool.event;

import com.whk.net.dispatchprotocol.MessageHandlerRecord;

public enum EventFactory {
    INSTANCE;

    public EventHandler create(Object message, MessageHandlerRecord record){
        return new EventHandler(message, record);
    }
}
