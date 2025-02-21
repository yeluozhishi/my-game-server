package com.whk.threadpool.processor;

import com.whk.threadpool.IDriver;
import com.whk.threadpool.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.RPCMessageHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class RPCMessageProcessor extends AbstractMessageProcessor<RPCMessageHandler> {

    private final Map<Integer, IDriver> driverMap = new HashMap<>();

    private final int size;

    private boolean mode = true;

    public RPCMessageProcessor() {
        ThreadPoolExecutor driver = ThreadPoolManager.getInstance().getExecutor(ThreadType.RPC_THREAD);
        for (int i = 0; i < driver.getMaximumPoolSize(); i++) {
            driverMap.put(i, new QueueDriver(driver, "RPC驱动器%d".formatted(i), new ConcurrentLinkedQueue<>()));
        }

        if ((driver.getMaximumPoolSize() & 1) == 0) {
            size = driver.getMaximumPoolSize() - 1;
        } else {
            size = driver.getMaximumPoolSize();
            mode = false;
        }
    }

    @Override
    public void message0(RPCMessageHandler handler) {
        // 固定驱动器
        if (mode) {
            driverMap.get(handler.getOrderId().hashCode() & size).addEvent(handler);
        } else {
            driverMap.get(handler.getOrderId().hashCode() % size).addEvent(handler);
        }
    }
}
