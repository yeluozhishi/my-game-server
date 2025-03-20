package com.whk.scene.event;

import com.whk.scene.map.AbstractScene;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class SceneTickEvent extends AbstractSceneEvent {

    private AbstractScene scene;

    public SceneTickEvent(AbstractScene scene) {
        super(scene.getSceneId());
        this.scene = scene;
    }

    @Override
    public ProcessorId getProcessorId() {
        return ProcessorId.MAP_PROCESSOR;
    }

    @Override
    public void doAction() {
        scene.getSkillProcessor().skillDeal();
        scene.sceneTick();
    }
}
