package com.whk.scene.map;

import com.whk.scene.AbstractSceneEvent;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.driver.QueueDriver;
import com.whk.threadpool.processor.AbstractMessageProcessor;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class SceneMessageProcessor extends AbstractMessageProcessor<AbstractSceneEvent> {

    @Override
    public IDriver addDriver(long id, ThreadPoolExecutor executor) {
        IDriver IDriver = new QueueDriver(executor, "玩家驱动器-%s".formatted(id), new ConcurrentLinkedQueue<>());
        getDriverMap().putIfAbsent(id, IDriver);
        return IDriver;
    }


    @Override
    public ThreadType getThreadType() {
        return ThreadType.SCENE_THREAD;
    }




    @Override
    public void message(AbstractSceneEvent handler) {
        var scene = SceneManager.INSTANCE.getScene(handler.getSceneId());
        if (Objects.nonNull(scene)) {
            scene.getDriver().addEvent(handler);
        }
    }

    @Override
    public void stop() {
        SceneManager.INSTANCE.stop();
    }
}
