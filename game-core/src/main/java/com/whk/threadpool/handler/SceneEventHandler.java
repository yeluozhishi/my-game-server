package com.whk.threadpool.handler;

import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
@Slf4j
public class SceneEventHandler extends AbstractHandler {

    private String sceneId;

    private Runnable futureTask;

    public SceneEventHandler() {
    }

    public SceneEventHandler(String sceneId, Runnable futureTask) {
        this.futureTask = futureTask;
        this.sceneId = sceneId;
    }

    @Override
    public ProcessorId getProcessorId() {
        return ProcessorId.MAP_PROCESSOR;
    }

    @Override
    public void execute() throws InvocationTargetException, IllegalAccessException {
        long time = System.currentTimeMillis();
        long diff = System.currentTimeMillis() - time;
        if (diff >= 20) log.info("SceneEvent exe time:%d".formatted(diff));
    }
}
