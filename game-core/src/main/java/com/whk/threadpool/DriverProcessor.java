package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 执行器
 */
public enum DriverProcessor {
    INSTANCE;
    // 驱动器池
    private final HashMap<Long, DriverInterface> drivers = new HashMap<>();

    private final DriverInterface dbDriver = new QueueDriver(ThreadPoolManager.getInstance().getExecutor(TheadType.DB_THREAD), new LinkedBlockingQueue<>());

    private DriverInterface addDriver(Long id, AbstractHandler eventHandler) {
        return drivers.put(id, new QueueDriver(eventHandler.getRecord().threadPoolExecutor(), new LinkedBlockingQueue<>()));
    }

    public void addEvent(Long id, AbstractHandler eventHandler) {
        drivers.getOrDefault(id, addDriver(id, eventHandler)).addEvent(eventHandler);
    }




    public void addDbEvent(Long id, AbstractHandler eventHandler){
        if (drivers.containsKey(id)){
            addEvent(id, eventHandler);
        } else {
            dbDriver.addEvent(eventHandler);
        }
    }
}
