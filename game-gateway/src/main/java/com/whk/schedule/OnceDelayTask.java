package com.whk.schedule;

import com.whk.server.GateServerManager;
import com.whk.threadpool.ThreadPoolManager;

import java.util.concurrent.TimeUnit;

/**
 * 延时任务
 */
public enum OnceDelayTask {
    INSTANCE;

    public void run(Runnable runnable, long delaySeconds) {
        ThreadPoolManager.getInstance().getScheduledThread().schedule(runnable, delaySeconds, TimeUnit.SECONDS);
    }
}
