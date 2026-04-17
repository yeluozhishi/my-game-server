package com.whk.threadpool.processor;

import com.whk.threadpool.ThreadType;
import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.driver.QueueDriver;
import com.whk.threadpool.handler.PlayerMessageHandler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class PlayerProcessor extends AbstractMessageProcessor<PlayerMessageHandler> {

    @Override
    public IDriver addDriver(long id, ThreadPoolExecutor executor) {
        IDriver IDriver = new QueueDriver(executor, "玩家驱动器-%s".formatted(id), new ConcurrentLinkedQueue<>());
        getDriverMap().putIfAbsent(id, IDriver);
        return IDriver;
    }


    @Override
    public ThreadType getThreadType() {
        return ThreadType.PLAYER_THREAD;
    }
}
