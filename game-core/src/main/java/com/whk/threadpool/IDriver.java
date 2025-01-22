package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

/**
 * 队列
 */
public interface IDriver {
    void addEvent(AbstractHandler eventHandler);

    AbstractHandler poll();
}
