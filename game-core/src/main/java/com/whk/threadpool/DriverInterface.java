package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

import java.util.List;

/**
 * 队列
 */
public interface DriverInterface {
    void addEvent(AbstractHandler eventHandler);

    void addAllEvent(List<AbstractHandler> handlerList);

    void setRunning(boolean running);

    AbstractHandler poll();
}
