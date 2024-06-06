package com.whk.threadpool;

import lombok.Getter;

import java.util.concurrent.*;

/**
 * 线程池管理
 */
@Getter
public enum ThreadPoolManager {
    // 实例
    DB_THREAD(new QueueExecutor("DB线程", 2, 4, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
    PLAYER_THREAD(new QueueExecutor("玩家线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
    RPC_THREAD(new QueueExecutor("rpc线程", 2, 4, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
    SCENE_THREAD(new QueueExecutor("场景线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
    SCHEDULED_THREAD((ThreadPoolExecutor) Executors.newScheduledThreadPool(3)),
    ;


    private final ThreadPoolExecutor threadPool;

    ThreadPoolManager(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

}