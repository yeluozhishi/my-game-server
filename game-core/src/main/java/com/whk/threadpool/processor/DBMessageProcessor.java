package com.whk.threadpool.processor;

import com.whk.threadpool.IDriver;
import com.whk.threadpool.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.AbstractHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class DBMessageProcessor extends AbstractMessageProcessor{

    private final Map<Long, IDriver> driverMap = new HashMap<>();

    private final int size;

    private boolean mode = true;

    public DBMessageProcessor() {
        ThreadPoolExecutor driver = ThreadPoolManager.getInstance().getExecutor(ThreadType.DB_THREAD);
        for (int i = 0; i < driver.getMaximumPoolSize(); i++) {
            driverMap.put((long) i, new QueueDriver(driver, "DB驱动器%d".formatted(i), new ConcurrentLinkedQueue<>()));
        }

        if ((driver.getMaximumPoolSize() & 1) == 0){
            size = driver.getMaximumPoolSize() - 1;
        } else {
            size = driver.getMaximumPoolSize();
            mode = false;
        }
    }

    @Override
    public void message(AbstractHandler abstractHandler) {
        if (mode){
            driverMap.get(abstractHandler.getOrderId() & size).addEvent(abstractHandler);
        } else {
            driverMap.get(abstractHandler.getOrderId() % size).addEvent(abstractHandler);
        }
    }
}
