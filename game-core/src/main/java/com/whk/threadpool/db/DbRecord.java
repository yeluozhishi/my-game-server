package com.whk.threadpool.db;

import com.whk.threadpool.handler.InstanceHandlerInterface;

import java.util.concurrent.ThreadPoolExecutor;


public record DbRecord(ThreadPoolExecutor threadPoolExecutor, Runnable runnable) implements InstanceHandlerInterface {
    @Override
    public void doAction(Object... message) {
        runnable.run();
    }
}

