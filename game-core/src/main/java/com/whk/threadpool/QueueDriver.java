package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 队列驱动器
 */
public class QueueDriver implements IDriver {
    private final ThreadPoolExecutor executor;

    private final Queue<AbstractHandler> eventHandlers;

    private final String name;

    private volatile boolean running = false;

    public QueueDriver(ThreadPoolExecutor executor, String name, Queue<AbstractHandler> eventHandlers) {
        this.executor = executor;
        this.eventHandlers = eventHandlers;
        this.name = name;
    }

    @Override
    public void addEvent(AbstractHandler eventHandler) {
        eventHandler.setDriver(this);
        synchronized (eventHandlers) {
            if (running) {
                eventHandlers.offer(eventHandler);
            } else {
                running = true;
                executor.execute(eventHandler);
            }
        }
    }

    @Override
    public void poll() {
        AbstractHandler handler;
        synchronized (eventHandlers) {
            handler = eventHandlers.poll();
            if (Objects.isNull(handler)) {
                running = false;
                return;
            }
        }
        executor.execute(handler);
    }

    @Override
    public String toString() {
        return name;
    }
}
