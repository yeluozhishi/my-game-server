package com.whk.threadpool;

import cn.hutool.core.thread.BlockPolicy;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池管理
 */
@Slf4j
public class ThreadPoolManager {

    private static final ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    // 处理数据库
    private ThreadPoolExecutor dbThread;
    // 处理玩家数据
    private ThreadPoolExecutor playerThread;
    // 处理场景数据
    private ThreadPoolExecutor sceneThread;
    // 处理循环事件
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    // 处理远程调用
    private ThreadPoolExecutor rpcThread;
    // 处理远程调用回调
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
            case GAME -> dbThread = new QueueExecutor("DB线程", 1, 4, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new BlockPolicy());

            case SCENE -> {
                sceneThread = new QueueExecutor("Scene线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
                dbThread = new QueueExecutor("DB线程", 1, 4, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new BlockPolicy());
            }
            case GATE, CLIENT ->
                    playerThread = new QueueExecutor("玩家线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
        }
        commonThreadPool();
    }

    private void commonThreadPool() {
        playerThread = new QueueExecutor("玩家线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
        rpcThread = new QueueExecutor("rpc线程", 1, 1, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
        rpcEventThread = new DefaultEventExecutorGroup(1, new DefaultThreadFactory("rpc延时任务线程"));
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(4,
                new ThreadFactory() {
                    final AtomicInteger count = new AtomicInteger(0);

                    @Override
                    public Thread newThread(@NotNull Runnable r) {
                        int curCount = count.incrementAndGet();
                        return new Thread(r, "定时器线程池-%d".formatted(curCount));
                    }
                });
    }

    public void closeThreadPool() {
        log.info("开始关闭线程");
        if (Objects.nonNull(scheduledThreadPoolExecutor)) scheduledThreadPoolExecutor.close();
        if (Objects.nonNull(playerThread)) playerThread.close();
        if (Objects.nonNull(sceneThread)) sceneThread.close();
        if (Objects.nonNull(rpcThread)) rpcThread.close();
        if (Objects.nonNull(rpcEventThread)) rpcEventThread.close();

        if (Objects.nonNull(dbThread)) dbThread.close();
        log.info("关闭线程完成");
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
            case SCHEDULED_THREAD -> {
                return scheduledThreadPoolExecutor;
            }
            case RPC_THREAD -> {
                return rpcThread;
            }
            default -> throw new RuntimeException("没有此类型线程池");
        }
    }
}