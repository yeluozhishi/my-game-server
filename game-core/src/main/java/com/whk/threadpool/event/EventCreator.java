package com.whk.threadpool.event;

import com.whk.threadpool.dispatchprotocol.MessageHandlerRecord;

public interface EventCreator {

    AbstractEventHandler create(MessageHandlerRecord method);

}
