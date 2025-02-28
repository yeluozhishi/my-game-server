package com.whk.threadpool.processor;

import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.driver.QueueDriver;
import com.whk.threadpool.handler.PlayerMessageHandler;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class PlayerProcessor extends AbstractMessageProcessor<PlayerMessageHandler> {

    protected IDriver addDriver(String id, ThreadPoolExecutor executor) {
        IDriver IDriver = new QueueDriver(executor, "玩家驱动器-%s".formatted(id), new ConcurrentLinkedQueue<>());
        getDriverMap().putIfAbsent(id, IDriver);
        return IDriver;
    }


    @Override
    public void message0(PlayerMessageHandler handler) {
        IDriver driver = getDriverMap().get(handler.getOrderId());
        if (Objects.isNull(driver)) {
            driver = addDriver(handler.getOrderId(), ThreadPoolManager.getInstance().getExecutor(ThreadType.PLAYER_THREAD));
        }
        driver.addEvent(handler);
    }
}
