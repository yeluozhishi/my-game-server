package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 执行器
 */
public enum DriverProcessor {
    INSTANCE;
    // 驱动器池
    private final HashMap<Long, DriverInterface> drivers = new HashMap<>(10, 0.5F);

    private final DriverInterface dbDriver = new QueueDriver(ThreadPoolManager.getInstance().getExecutor(ThreadType.DB_THREAD), new LinkedBlockingQueue<>());

    private DriverInterface addDriver(Long id, ThreadPoolExecutor executor) {
        return drivers.put(id, new QueueDriver(executor, new LinkedBlockingQueue<>()));
    }

    public void addMessageHandler(Long id, AbstractHandler eventHandler) {
        drivers.getOrDefault(id, addDriver(id, ThreadPoolManager.getInstance().getExecutor(eventHandler.getRecord().threadType())))
                .addEvent(eventHandler);
    }


    public void addEventHandler(Long id, AbstractHandler eventHandler) {
        drivers.getOrDefault(id, addDriver(id, ThreadPoolManager.getInstance().getExecutor(eventHandler.getRecord().threadType())))
                .addEvent(eventHandler);
    }

    public void addDbHandler(Long id, AbstractHandler eventHandler) {
        // 各自执行，保证串行
        if (drivers.containsKey(id)) {
            addMessageHandler(id, eventHandler);
        } else {
            dbDriver.addEvent(eventHandler);
        }
    }
}
