package com.whk.threadpool;

import java.util.concurrent.LinkedBlockingQueue;

public class DriverFactory {

    public static QueueDriver createDriver(ThreadPoolManager manager) throws Exception {
        QueueDriver driver = null;
        switch (manager){
            case PLAYER_THREAD -> createPlayerDriver();
            case SCENE_THREAD -> createSceneDriver();
            default -> throw new Exception("NO type match");
        }
        return driver;
    }

    public static QueueDriver createPlayerDriver(){
        return new QueueDriver((QueueExecutor) ThreadPoolManager.PLAYER_THREAD.getThreadPool(), new LinkedBlockingQueue<>());
    }

    public static QueueDriver createSceneDriver(){
        return new QueueDriver((QueueExecutor) ThreadPoolManager.SCENE_THREAD.getThreadPool(), new LinkedBlockingQueue<>());
    }

}
