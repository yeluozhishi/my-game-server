package com.whk.scene;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class SceneTickEvent extends com.whk.scene.AbstractSceneEvent {

    private AbstractScene scene;

    public SceneTickEvent(AbstractScene scene) {
        super(scene.getSceneId());
        this.scene = scene;
    }

    @Override
    public void doAction() {
        scene.sceneTick();
    }
}
