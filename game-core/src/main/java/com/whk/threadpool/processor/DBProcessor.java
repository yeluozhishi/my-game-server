package com.whk.threadpool.processor;

import com.whk.threadpool.driver.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.DbHandler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class DBProcessor extends AbstractMessageProcessor<DbHandler> {

    private final int size;

    private boolean mode = true;

    public DBProcessor() {
        ThreadPoolExecutor driver = ThreadPoolManager.getInstance().getExecutor(ThreadType.DB_THREAD);
        for (int i = 0; i < driver.getMaximumPoolSize(); i++) {
            getDriverMap().put(String.valueOf(i), new QueueDriver(driver, "DB驱动器%d".formatted(i), new ConcurrentLinkedQueue<>()));
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
            getDriverMap().get(handler.getOrderId()).addEvent(handler);
        } else {
            getDriverMap().get(handler.getOrderId()).addEvent(handler);
        }
    }

    @Override
    public void removeDriver(String id) {

    }
}
