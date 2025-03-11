package com.whk.scene.map;

import com.whk.threadpool.handler.SceneEventHandler;
import com.whk.threadpool.processor.AbstractMessageProcessor;

import java.util.Objects;

public class SceneMessageProcessor extends AbstractMessageProcessor<SceneEventHandler> {

    @Override
    public void message0(SceneEventHandler handler) {
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
