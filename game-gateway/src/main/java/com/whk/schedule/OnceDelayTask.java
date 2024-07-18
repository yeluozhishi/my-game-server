package com.whk.schedule;

import com.whk.server.GateServerManager;
import com.whk.threadpool.ThreadPoolManager;

import java.util.concurrent.TimeUnit;

/**
 * 延时任务
 */
public class OnceDelayTask {

    public void delayTask(){
        GateServerManager.getInstance().requestServers();
    }

    public void run() {
        ThreadPoolManager.getInstance().getScheduledThread().schedule(this::delayTask, 30, TimeUnit.SECONDS);
    }
}
