package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

/**
 * 队列
 */
public interface DriverInterface {
    void addEvent(AbstractHandler eventHandler);

    AbstractHandler poll();
}
