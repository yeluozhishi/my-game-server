package com.whk.threadpool;

import com.whk.threadpool.event.AbstractEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * 线程池
 */
public class QueueExecutor extends ThreadPoolExecutor {
    private static final Logger log = LoggerFactory.getLogger(QueueExecutor.class);
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
            log.error("{}出错：{}", name, r.getRecord().toString());
        }
    }

}
