package com.whk.threadpool;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * 线程组
 */
public enum Executor {
    // 实例
    INSTANCE;

    /**
     * db线程组
      */
    private EventExecutorGroup dbExecutorGroup;

    public void setInfo(int dbGroupNum){
        dbExecutorGroup = new DefaultEventExecutorGroup(dbGroupNum);
    }

    public EventExecutor getDbExecutorGroup() {
        return dbExecutorGroup.next();
    }

    public void dbExecutorTry(Runnable task){
        try {
            dbExecutorGroup.next().execute(task);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Optional<Object> dbExecutorTry(Callable task){
        try {
            return Optional.ofNullable(dbExecutorGroup.next().submit(task).get());
        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }


}