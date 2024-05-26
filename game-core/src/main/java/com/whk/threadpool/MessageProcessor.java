package com.whk.threadpool;

import com.whk.threadpool.event.EventHandler;

import java.util.HashMap;

/**
 * 执行器
 */
public enum MessageProcessor {
    INSTANCE;
    // 驱动器池
    private final HashMap<Long, QueueDriver> drivers = new HashMap<>();

    private QueueDriver addDriver(Long id, EventHandler eventHandler) throws Exception {
        return drivers.put(id, DriverFactory.createDriver(eventHandler.getRecord().poolManager()));
    }

    public void addEvent(Long id, EventHandler eventHandler) throws Exception {
        drivers.getOrDefault(id, addDriver(id, eventHandler)).addEvent(eventHandler);
    }
}
