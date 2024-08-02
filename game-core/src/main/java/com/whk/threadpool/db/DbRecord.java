package com.whk.threadpool.db;

import com.whk.threadpool.handler.HandlerInterface;

import java.util.concurrent.ThreadPoolExecutor;


public record DbRecord(ThreadPoolExecutor threadPoolExecutor, Runnable runnable) implements HandlerInterface {
    @Override
    public void doAction(Object... message) {
        runnable.run();
    }
}

