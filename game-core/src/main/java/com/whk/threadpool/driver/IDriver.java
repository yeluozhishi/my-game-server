package com.whk.threadpool.driver;

import com.whk.threadpool.handler.AbstractHandler;

/**
 * 队列
 */
public interface IDriver {
    void addEvent(AbstractHandler eventHandler);

    void poll();

    String getName();

    void stop();
}
