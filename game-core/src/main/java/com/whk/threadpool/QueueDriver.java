package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 驱动器
 */
public class QueueDriver implements DriverInterface {
    private final ThreadPoolExecutor executor;

    private final Queue<AbstractHandler> eventHandlers;
    private Boolean running = false;

    public QueueDriver(ThreadPoolExecutor executor, Queue<AbstractHandler> eventHandlers) {
        this.executor = executor;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void addEvent(AbstractHandler eventHandler){
        eventHandler.setDriver(this);
        eventHandlers.offer(eventHandler);
        if (!running){
            executor.execute(Objects.requireNonNull(eventHandlers.poll()));
        }
    }

    @Override
    public boolean isEmpty() {
        if (eventHandlers.isEmpty()) running = false;
        return eventHandlers.isEmpty();
    }

    @Override
    public AbstractHandler poll() {
        return eventHandlers.poll();
    }
}
