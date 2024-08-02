package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

public interface DriverInterface {
    void addEvent(AbstractHandler eventHandler);

    boolean isEmpty();

    AbstractHandler poll();
}
