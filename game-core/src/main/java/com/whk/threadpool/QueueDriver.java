package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 驱动器
 * 问题：会残留一个或几个任务
 */
public class QueueDriver implements IDriver {
    private final ThreadPoolExecutor executor;

    private final Queue<AbstractHandler> eventHandlers;

    private final String name;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public QueueDriver(ThreadPoolExecutor executor, String name, Queue<AbstractHandler> eventHandlers) {
        this.executor = executor;
        this.eventHandlers = eventHandlers;
        this.name = name;
    }

    @Override
    public void addEvent(AbstractHandler eventHandler) {
        eventHandler.setDriver(this);
        if (running.compareAndSet(false, true)) {
            executor.execute(eventHandler);
        } else {
            eventHandlers.offer(eventHandler);
        }
    }

    @Override
    public AbstractHandler poll() {
        AbstractHandler handler = eventHandlers.poll();
        if (Objects.isNull(handler)) running.compareAndSet(true, false);
        return handler;
    }

    @Override
    public String toString() {
        return name;
    }
}
