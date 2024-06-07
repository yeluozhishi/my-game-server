package com.whk.threadpool;

import com.whk.threadpool.event.EventHandler;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 执行器
 */
public enum MessageProcessor {
    INSTANCE;
    // 驱动器池
    private final HashMap<Long, QueueDriver> drivers = new HashMap<>();

    private QueueDriver addDriver(Long id, EventHandler eventHandler) {
        return drivers.put(id, new QueueDriver((QueueExecutor) eventHandler.getRecord().threadPoolExecutor(), new LinkedBlockingQueue<>()));
    }

    public void addEvent(Long id, EventHandler eventHandler) {
        drivers.getOrDefault(id, addDriver(id, eventHandler)).addEvent(eventHandler);
    }
}
