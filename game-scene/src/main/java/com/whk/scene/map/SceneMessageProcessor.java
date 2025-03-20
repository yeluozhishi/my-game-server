package com.whk.scene.map;

import com.whk.scene.event.AbstractSceneEvent;
import com.whk.threadpool.processor.AbstractMessageProcessor;

import java.util.Objects;

public class SceneMessageProcessor extends AbstractMessageProcessor<AbstractSceneEvent> {

    @Override
    public void message0(AbstractSceneEvent handler) {
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
