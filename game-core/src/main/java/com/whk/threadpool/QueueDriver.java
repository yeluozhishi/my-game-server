package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 驱动器
 */
public class QueueDriver implements DriverInterface {
    private final ThreadPoolExecutor executor;

    private final Queue<AbstractHandler> eventHandlers;

    private String name;

    private volatile boolean running = false;

    public QueueDriver(ThreadPoolExecutor executor, String name, Queue<AbstractHandler> eventHandlers) {
        this.executor = executor;
        this.eventHandlers = eventHandlers;
        this.name = name;
    }

    @Override
    public void addEvent(AbstractHandler eventHandler){
        eventHandler.setDriver(this);
        eventHandlers.offer(eventHandler);
        if (!running){
            running = true;
            executor.execute(Objects.requireNonNull(eventHandlers.poll()));
        }
    }

    @Override
    public void addAllEvent(List<AbstractHandler> handlerList){
        for (AbstractHandler eventHandler : handlerList) {
            eventHandler.setDriver(this);
            eventHandlers.offer(eventHandler);
        }
        if (!running){
            running = true;
            executor.execute(Objects.requireNonNull(eventHandlers.poll()));
        }
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public AbstractHandler poll() {
        return eventHandlers.poll();
    }

    @Override
    public String toString() {
        return name;
    }
}
