package com.whk.scene.map;

import com.whk.threadpool.handler.SceneHandler;
import com.whk.threadpool.processor.AbstractMessageProcessor;

import java.util.Objects;

public class SceneMessageProcessor extends AbstractMessageProcessor<SceneHandler> {

    @Override
    public void message0(SceneHandler handler) {
        var scene = SceneManager.INSTANCE.getScene(handler.getSceneId());
        if (Objects.nonNull(scene)) {
            scene.getDriver().addEvent(handler);
        }
    }
}
