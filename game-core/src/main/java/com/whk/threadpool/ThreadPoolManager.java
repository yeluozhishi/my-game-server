package com.whk.threadpool;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Getter;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 线程池管理
 */
public class ThreadPoolManager {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private static final ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    private ThreadPoolExecutor dbThread;
    private ThreadPoolExecutor playerThread;
    private ThreadPoolExecutor sceneThread;

    private ThreadPoolExecutor eventThread;

    @Getter
    private DefaultEventExecutorGroup rpcThread;

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
                sceneThread = new QueueExecutor("场景线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
            }

            case GAME_SCENE ->
                    sceneThread = new QueueExecutor("场景线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        }
        commonThreadPool();
    }

    private void commonThreadPool() {
        playerThread = new QueueExecutor("玩家线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        eventThread = new ThreadPoolExecutor(1, 1, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        rpcThread = new DefaultEventExecutorGroup(2, new DefaultThreadFactory("rpc线程"));
    }

    public void closeThreadPool() {
        System.out.println("开始关闭线程");
        if (Objects.nonNull(eventThread)) eventThread.close();
        if (Objects.nonNull(playerThread)) playerThread.close();
        if (Objects.nonNull(sceneThread)) sceneThread.close();
        if (Objects.nonNull(rpcThread)) rpcThread.close();


        if (Objects.nonNull(dbThread)) dbThread.close();
        System.out.println("关闭线程完成");
    }


    public ThreadPoolExecutor getExecutor(TheadType theadType) {
        switch (theadType) {
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
            default -> {
                return null;
            }
        }
    }
}