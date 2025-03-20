package com.whk.threadpool.driver;

import com.whk.threadpool.handler.IQueueCommand;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 队列驱动器
 */

@Getter
@Slf4j
public class QueueDriver implements IDriver {
    private final ThreadPoolExecutor executor;

    private final Queue<IQueueCommand> eventHandlers;

    private final String name;

    private volatile boolean running = false;

    public QueueDriver(ThreadPoolExecutor executor, String name, Queue<IQueueCommand> eventHandlers) {
        this.executor = executor;
        this.eventHandlers = eventHandlers;
        this.name = name;
    }

    @Override
    public void addEvent(IQueueCommand eventHandler) {
        eventHandler.setDriver(this);
        if (eventHandlers.size() > 100) log.error("驱动器" + name + "队列任务堆积：" + eventHandlers.size());
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
        IQueueCommand handler;
        synchronized (eventHandlers) {
            handler = eventHandlers.poll();
            if (Objects.isNull(handler)) {
                running = false;
            } else {
                executor.execute(handler);
            }
        }
    }

    public void stop() {
        while (true) {
            IQueueCommand handler = eventHandlers.poll();
            if (handler != null) {
                handler.run();
            } else {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
