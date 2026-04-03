package com.whk.scene.event;

import com.whk.threadpool.event.AbstractEventHandler;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class AbstractSceneEvent extends AbstractEventHandler {

    private String sceneId;

    public AbstractSceneEvent(String sceneId) {
        super(sceneId);
        this.sceneId = sceneId;
    }

    @Override
    public ProcessorId getProcessorId() {
        return ProcessorId.MAP_PROCESSOR;
    }
}
