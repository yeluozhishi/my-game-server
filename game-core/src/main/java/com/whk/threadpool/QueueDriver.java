package com.whk.threadpool;

import com.whk.threadpool.messagehandler.AbstractMessageHandler;

import java.util.Objects;
import java.util.Queue;

/**
 * 驱动器
 */
public class QueueDriver implements DriverInterface {
    private final QueueExecutor executor;

    private final Queue<AbstractMessageHandler> eventHandlers;
    private Boolean running = false;

    public QueueDriver(QueueExecutor executor, Queue<AbstractMessageHandler> eventHandlers) {
        this.executor = executor;
        this.eventHandlers = eventHandlers;
    }

    public void addEvent(AbstractMessageHandler eventHandler){
        eventHandler.setDriverInterface(this);
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
    public AbstractMessageHandler poll() {
        return eventHandlers.poll();
    }
}
