package com.whk.threadpool;

import com.whk.threadpool.handler.AbstractHandler;

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
        var m = (AbstractHandler) r;
        if (!m.getDriver().isEmpty()){
            execute(m.getDriver().poll());
        }
        if (Objects.nonNull(t)){
            t.printStackTrace();
            logger.severe("%s出错：%s ".formatted(name, m.getRecord().toString()));
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return super.newTaskFor(callable);
    }
}
