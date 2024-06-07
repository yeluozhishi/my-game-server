package com.whk.threadpool;

import com.whk.threadpool.event.AbstractEventHandler;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * 线程池
 */
public class QueueExecutor extends ThreadPoolExecutor {
    private static final Logger logger = Logger.getLogger(QueueExecutor.class.getName());
    private final String name;

    public QueueExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.name = name;
    }

    public QueueExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.name = name;
    }

    public QueueExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        this.name = name;
    }

    public QueueExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, java.util.concurrent.TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.name = name;
    }



    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        afterExecute1((AbstractEventHandler) r, t);
    }

    protected void afterExecute1(AbstractEventHandler r, Throwable t) {
        if (!r.getDriverInterface().isEmpty()){
            submit(r.getDriverInterface().poll());
        }
        if (Objects.nonNull(t)){
            logger.severe(STR."\{name}出错：\{r.getRecord().toString()}");
        }
    }

}