package com.whk.threadpool;

import com.whk.threadpool.event.AbstractEventHandler;

import java.util.Queue;

/**
 * 驱动器
 */
public class QueueDriver implements DriverInterface {
    private QueueExecutor executor;

    private Queue<AbstractEventHandler> eventHandlers;
    private Boolean running = false;

    public QueueDriver(QueueExecutor executor, Queue<AbstractEventHandler> eventHandlers) {
        this.executor = executor;
        this.eventHandlers = eventHandlers;
    }

    public void addEvent(AbstractEventHandler eventHandler){
        eventHandler.setDriverInterface(this);
        eventHandlers.offer(eventHandler);
        if (!running){
            executor.submit(eventHandlers.poll());
        }
    }

    @Override
    public boolean isEmpty() {
        if (eventHandlers.isEmpty()) running = false;
        return eventHandlers.isEmpty();
    }

    @Override
    public AbstractEventHandler poll() {
        return eventHandlers.poll();
    }
}
