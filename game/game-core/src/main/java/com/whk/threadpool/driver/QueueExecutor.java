package com.whk.threadpool.driver;

import com.whk.threadpool.handler.IQueueCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 */
@Slf4j
public class QueueExecutor extends ThreadPoolExecutor {
    private final String name;

    public QueueExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler rejectedExecutionHandler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new ThreadFactory() {
            final AtomicInteger count = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                int curCount = this.count.incrementAndGet();
                log.info("创建线程:%s-%d".formatted(name, curCount));
                return new Thread(r, "%s-%d".formatted(name, curCount));
            }
        }, rejectedExecutionHandler);
        this.name = name;
    }


    /**
     * 执行完成后，从队列中获取新的任务，通过execute继续执行新任务。
     *
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     *          execution completed normally
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        var m = (IQueueCommand) r;
        m.getDriver().poll();
        if (Objects.nonNull(t)) {
            log.error("%s出错：%s  %s".formatted(name, m.toString(), t.getStackTrace()));
        }
    }
}
