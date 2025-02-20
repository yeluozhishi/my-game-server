package com.whk.threadpool.processor;

import com.whk.threadpool.IDriver;
import com.whk.threadpool.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.DbHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class DBMessageProcessor extends AbstractMessageProcessor<DbHandler> {

    private final Map<Integer, IDriver> driverMap = new HashMap<>();

    private final int size;

    private boolean mode = true;

    public DBMessageProcessor() {
        ThreadPoolExecutor driver = ThreadPoolManager.getInstance().getExecutor(ThreadType.DB_THREAD);
        for (int i = 0; i < driver.getMaximumPoolSize(); i++) {
            driverMap.put(i, new QueueDriver(driver, "DB驱动器%d".formatted(i), new ConcurrentLinkedQueue<>()));
        }

        if ((driver.getMaximumPoolSize() & 1) == 0) {
            size = driver.getMaximumPoolSize() - 1;
        } else {
            size = driver.getMaximumPoolSize();
            mode = false;
        }
    }

    @Override
    public void message0(DbHandler handler) {
        // 固定驱动器
        if (mode) {
            driverMap.get(handler.getOrderId().hashCode() & size).addEvent(handler);
        } else {
            driverMap.get(handler.getOrderId().hashCode() % size).addEvent(handler);
        }
    }
}
