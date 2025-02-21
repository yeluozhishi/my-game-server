package com.whk.threadpool.handler;

import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
public class SceneEventHandler extends AbstractHandler {

    private String sceneId;

    private final Runnable futureTask;

    public SceneEventHandler(Runnable futureTask) {
        this.futureTask = futureTask;
    }

    @Override
    public ProcessorId getProcessorId() {
        return ProcessorId.MAP_PROCESSOR;
    }

    @Override
    public void execute() throws InvocationTargetException, IllegalAccessException {
        long time = System.currentTimeMillis();
        futureTask.run();
        logger.info("SceneEvent exe time:%d".formatted(System.currentTimeMillis() - time));
    }
}
