package com.whk.threadpool;

import com.whk.threadpool.handler.DbHandler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 执行器
 */
public enum DriverProcessor {

    INSTANCE;

    private final ThreadPoolExecutor executor = ThreadPoolManager.getInstance().getExecutor(ThreadType.DB_THREAD);

    public void addDbHandler(DbHandler dbHandler) {
        executor.execute(dbHandler);
    }

}
