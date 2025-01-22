package com.whk.threadpool.processor;

import com.whk.threadpool.IDriver;
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
    private final HashMap<Long, IDriver> drivers = new HashMap<>(10, 0.5F);

    protected IDriver addDriver(long id, ThreadPoolExecutor executor) {
        IDriver IDriver = new QueueDriver(executor, "玩家驱动器-%d".formatted(id), new ConcurrentLinkedQueue<>());
        drivers.putIfAbsent(id, IDriver);
        return IDriver;
    }

    @Override
    public void message(AbstractHandler handler) {
        IDriver IDriver = drivers.get(handler.getOrderId());
        if (Objects.isNull(IDriver)) {
            IDriver = addDriver(handler.getOrderId(), ThreadPoolManager.getInstance().getExecutor(ThreadType.PLAYER_THREAD));
        }
        IDriver.addEvent(handler);
    }
}
