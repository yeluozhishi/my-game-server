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


    /**
     * ThreadPoolExecutor.submit(Runnable task)
     * 将task封装到FutureTask，task成为callable，执行完后清除了。
     *
     *
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     * execution completed normally
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        var m = (AbstractEventHandler) r;
        if (!m.getDriverInterface().isEmpty()){
            execute(m.getDriverInterface().poll());
        }
        if (Objects.nonNull(t)){
            logger.severe("%s出错：%s".formatted(name, m.getRecord().toString()));
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return super.newTaskFor(callable);
    }
}
