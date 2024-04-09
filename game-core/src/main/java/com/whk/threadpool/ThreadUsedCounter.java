package com.whk.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUsedCounter {

    private ExecutorService executorService;

    private final AtomicInteger threadUsedCount = new AtomicInteger(0);

    ThreadUsedCounter(ExecutorService executorService){
        this.executorService = executorService;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public int incrementUsed(){
        return threadUsedCount.getAndIncrement();
    }

    public int getUsedCount(){
        return threadUsedCount.get();
    }

    public int decrementUsed(){
        return threadUsedCount.getAndDecrement();
    }

    public void shutdown(){
        executorService.shutdown();
    }
}
