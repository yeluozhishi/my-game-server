package com.whk.threadpool;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Getter;

import java.util.concurrent.*;

/**
 * 线程池管理
 */
@Getter
public class ThreadPoolManager {

    private static final ThreadPoolManager threadPoolManager = new ThreadPoolManager();


    private ThreadPoolExecutor dbThread;
    private ThreadPoolExecutor playerThread;
    private ThreadPoolExecutor gateThread;
    private ThreadPoolExecutor sceneThread;

    private DefaultEventExecutorGroup rpcThread;
    private ScheduledExecutorService scheduledThread;
//    DB_THREAD(new QueueExecutor("DB线程", 2, 4, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
//    PLAYER_THREAD(new QueueExecutor("玩家线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
//    GATE_THREAD(new QueueExecutor("网关线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
//    RPC_THREAD(new QueueExecutor("rpc线程", 2, 4, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
//    SCENE_THREAD(new QueueExecutor("场景线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>())),
//    SCHEDULED_THREAD((ThreadPoolExecutor) Executors.newScheduledThreadPool(3)),
//    ;


    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance(){
        return threadPoolManager;
    }

    /**
     * 根据需求创建线程池
     * @param serverType 服务器类型
     */
    public void initThreadPool(ServerType serverType) {
        switch (serverType) {
            case GAME -> {
                dbThread = new QueueExecutor("DB线程", 2, 4, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
                playerThread = new QueueExecutor("玩家线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
                sceneThread = new QueueExecutor("场景线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
                rpcThread = new DefaultEventExecutorGroup(2, new DefaultThreadFactory("rpc线程"));
            }
            case GATE -> {
                gateThread = new QueueExecutor("网关线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
                rpcThread = new DefaultEventExecutorGroup(2, new DefaultThreadFactory("rpc线程"));
            }
            case GAME_SCENE -> {
                sceneThread = new QueueExecutor("场景线程", 8, 16, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
            }

        }

        scheduledThread = Executors.newScheduledThreadPool(2);
    }

    public ThreadPoolExecutor getExecutor(TheadType theadType) {
        switch (theadType){
            case DB_THREAD ->
            {
                return getDbThread();
            }
            case SCENE_THREAD -> {
                return getSceneThread();
            }
            case PLAYER_THREAD -> {
                return getPlayerThread();
            }
            default -> {
                return null;
            }
        }
    }
}