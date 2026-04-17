package com.whk.threadpool.processor;

import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.driver.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.DbHandler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class DBProcessor extends AbstractMessageProcessor<DbHandler> {

    @Override
    protected IDriver addDriver(long id, ThreadPoolExecutor executor) {
        for (long i = 0; i < executor.getMaximumPoolSize(); i++) {
            getDriverMap().put(i, new QueueDriver(executor, "DB驱动器%d".formatted(i), new ConcurrentLinkedQueue<>()));
        }
        return null;
    }

    @Override
    public ThreadType getThreadType() {
        return ThreadType.DB_THREAD;
    }

    public DBProcessor() {
        ThreadPoolExecutor driver = ThreadPoolManager.getInstance().getExecutor(getThreadType());
        addDriver(0, driver);
    }

    @Override
    public void message(DbHandler handler) {
        // 固定驱动器
        getDriverMap().get(handler.getOrderId() % getDriverMap().size()).addEvent(handler);
    }

    @Override
    public void removeDriver(String id) {

    }
}
