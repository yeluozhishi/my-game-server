package com.whk.threadpool.handler;

import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SceneEventHandler extends AbstractHandler {

    private Object message;

    private String sceneId;

    public SceneEventHandler(IRecord record) {
        super(record);
    }

    @Override
    public ProcessorId getProcessorId() {
        return ProcessorId.MAP_PROCESSOR;
    }


    @Override
    public void run() {
        getRecord().doAction(message);
    }

}
