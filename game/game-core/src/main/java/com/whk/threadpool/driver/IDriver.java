package com.whk.threadpool.driver;

import com.whk.threadpool.handler.IQueueCommand;

/**
 * 队列
 */
public interface IDriver {
    void addEvent(IQueueCommand eventHandler);

    void poll();

    String getName();

    void stop();
}
