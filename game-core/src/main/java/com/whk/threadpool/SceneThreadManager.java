package com.whk.threadpool;


import com.whk.scene.Scene;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * 场景线程池管理
 */
public class SceneThreadManager {

    private final Logger logger = Logger.getLogger(SceneThreadManager.class.getName());

    // 线程引用计数
    private final List<ThreadUsedCounter> threads = new ArrayList<>();

    private final ConcurrentHashMap<String, ExecutorService> sceneThread = new ConcurrentHashMap<>();

    SceneThreadManager() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; i++) {
            ThreadUsedCounter t = new ThreadUsedCounter(Executors.newFixedThreadPool(1, new NamedThreadFactory(i + "scene")));
            threads.add(t);
        }
    }


    public void bindThreadToScene(String sceneId) {
        threads.sort(Comparator.comparingInt(ThreadUsedCounter::getUsedCount));
        ThreadUsedCounter threadUsedCounter = threads.get(0);
        threadUsedCounter.incrementUsed();
        sceneThread.put(sceneId, threadUsedCounter.getExecutorService());
    }

    public void putEventToSceneQueue(String sceneId, Runnable runnable){
        if (sceneThread.containsKey(sceneId)){
            sceneThread.get(sceneId).execute(runnable);
        } else {
            logger.severe("ERROR: scene thread not found, sceneId: " + sceneId);
        }
    }

    /**
     * 关闭
     */
    public void shutdown(){
        sceneThread.clear();
        threads.forEach(ThreadUsedCounter::shutdown);
    }

    public static void main(String[] args) {
        Scene scene = new Scene();
        SceneThreadManager sceneThreadManager = new SceneThreadManager();
        sceneThreadManager.bindThreadToScene(scene.SceneId());
        sceneThreadManager.putEventToSceneQueue(scene.SceneId(), scene::tick);
        sceneThreadManager.shutdown();
    }
}
