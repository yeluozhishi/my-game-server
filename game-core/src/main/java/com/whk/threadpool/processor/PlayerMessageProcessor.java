package com.whk.threadpool.processor;

import com.whk.threadpool.DriverInterface;
import com.whk.threadpool.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.AbstractHandler;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class PlayerMessageProcessor extends AbstractMessageProcessor {

    // 驱动器池
    private final HashMap<Long, DriverInterface> drivers = new HashMap<>(10, 0.5F);

    protected DriverInterface addDriver(long id, ThreadPoolExecutor executor) {
        DriverInterface driverInterface = new QueueDriver(executor, "玩家驱动器-%d".formatted(id), new ConcurrentLinkedQueue<>());
        drivers.putIfAbsent(id, driverInterface);
        return driverInterface;
    }

    @Override
    public void message(AbstractHandler handler) {
        DriverInterface driverInterface = drivers.get(handler.getOrderId());
        if (Objects.isNull(driverInterface)) {
            driverInterface = addDriver(handler.getOrderId(), ThreadPoolManager.getInstance().getExecutor(ThreadType.PLAYER_THREAD));
        }
        driverInterface.addEvent(handler);
    }
}
