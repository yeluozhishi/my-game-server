package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * 线程池
 */
public class QueueExecutor extends ThreadPoolExecutor {
    private static final Logger logger = Logger.getLogger(QueueExecutor.class.getName());
    private final String name;

    public QueueExecutor(String name, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new ThreadFactory() {
            final AtomicInteger count = new AtomicInteger(0);

            public Thread newThread(@NotNull Runnable r) {
                int curCount = this.count.incrementAndGet();
                logger.info("创建线程:%s-%d".formatted(name, curCount));
                return new Thread(r, "%s-%d".formatted(name, curCount));
            }
        });
        this.name = name;
    }


    /**
     * ThreadPoolExecutor.submit(Runnable task)
     * 将task封装到FutureTask，task成为callable，执行完后清除了。
     *
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     *          execution completed normally
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        var m = (AbstractHandler) r;
        var task = m.getDriver().poll();
        if (Objects.nonNull(task)) {
            execute(task);
        } else {
            m.getDriver().setRunning(false);
        }
        if (Objects.nonNull(t)) {
            logger.severe("%s出错：%s  %s".formatted(name, m.getRecord().toString(), t.getStackTrace()));
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return super.newTaskFor(callable);
    }
}
