package com.whk.threadpool;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理
 */
public class ThreadPoolManager {

    private static final ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    private ThreadPoolExecutor dbThread;
    private ThreadPoolExecutor playerThread;
    private ThreadPoolExecutor sceneThread;

    private ThreadPoolExecutor eventThread;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private ThreadPoolExecutor rpcThread;

    @Getter
    private DefaultEventExecutorGroup rpcEventThread;

    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }

    /**
     * 根据需求创建线程池
     *
     * @param serverType 服务器类型
     */
    public void initThreadPool(ServerType serverType) {
        switch (serverType) {
            case GAME -> {
                dbThread = new QueueExecutor("DB线程", 2, 4, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
                sceneThread = new QueueExecutor("Scene线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
            }

            case GAME_SCENE -> sceneThread = new QueueExecutor("Scene线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        }
        commonThreadPool();
    }

    private void commonThreadPool() {
        playerThread = new QueueExecutor("玩家线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        eventThread = new ThreadPoolExecutor(1, 1, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        rpcThread = new ThreadPoolExecutor(1, 1, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        rpcEventThread = new DefaultEventExecutorGroup(1, new DefaultThreadFactory("rpc延时任务线程"));;
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
    }

    public void closeThreadPool() {
        System.out.println("开始关闭线程");
        if (Objects.nonNull(scheduledThreadPoolExecutor)) scheduledThreadPoolExecutor.close();
        if (Objects.nonNull(eventThread)) eventThread.close();
        if (Objects.nonNull(playerThread)) playerThread.close();
        if (Objects.nonNull(sceneThread)) sceneThread.close();
        if (Objects.nonNull(rpcThread)) rpcThread.close();
        if (Objects.nonNull(rpcEventThread)) rpcEventThread.close();

        if (Objects.nonNull(dbThread)) dbThread.close();
        System.out.println("关闭线程完成");
    }


    public ThreadPoolExecutor getExecutor(ThreadType threadType) {
        switch (threadType) {
            case DB_THREAD -> {
                return dbThread;
            }
            case SCENE_THREAD -> {
                return sceneThread;
            }
            case PLAYER_THREAD -> {
                return playerThread;
            }
            case EVENT_THREAD -> {
                return eventThread;
            }
            case SCHEDULED_THREAD -> {
                return scheduledThreadPoolExecutor;
            }
            case RPC_THREAD -> {
                return rpcThread;
            }
            default -> {
                return null;
            }
        }
    }
}