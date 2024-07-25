package com.whk.threadpool;

import com.whk.threadpool.messagehandler.AbstractMessageHandler;

public interface DriverInterface {
    public boolean isEmpty();

    public AbstractMessageHandler poll();
}
