package com.whk.threadpool.processor;

import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.driver.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.DbHandler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class DBProcessor extends AbstractMessageProcessor<DbHandler> {

    private boolean mode = true;

    @Override
    protected IDriver addDriver(String id, ThreadPoolExecutor executor) {
        for (int i = 0; i < executor.getMaximumPoolSize(); i++) {
            getDriverMap().put(String.valueOf(i), new QueueDriver(executor, "DB驱动器%d".formatted(i), new ConcurrentLinkedQueue<>()));
        }
        return null;
    }

    @Override
    public ThreadType getThreadType() {
        return ThreadType.DB_THREAD;
    }

    public DBProcessor() {
        ThreadPoolExecutor driver = ThreadPoolManager.getInstance().getExecutor(getThreadType());
        addDriver("0", driver);
        mode = (driver.getMaximumPoolSize() & 1) == 0;
    }

    @Override
    public void message(DbHandler handler) {
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
