package com.whk.threadpool.handler;

import com.whk.dispatchprotocol.PlayerMessageRecord;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@Getter
@Setter
@Slf4j
public class ScenePlayerMessageHandler extends SceneEventHandler {

    private Object message;

    private PlayerMessageRecord record;

    private long playerId;


    public ScenePlayerMessageHandler(String sceneId, Object message, long playerId, PlayerMessageRecord record) {
        this.setSceneId(sceneId);
        this.message = message;
        this.record = record;
        this.playerId = playerId;
    }


    @Override
    public ProcessorId getProcessorId() {
        return ProcessorId.MAP_PROCESSOR;
    }


    @Override
    public void run() {
        try {
            execute();
        } catch (Exception e) {
            log.error("PlayerMessage %s; info: %s; stack: %s".formatted(record.method().getName(), e.getMessage(), Arrays.toString(e.getStackTrace())));
        }
    }

    @Override
    public void execute() throws InvocationTargetException, IllegalAccessException {
        long time = System.currentTimeMillis();
        record.method().invoke(record.clazz(), message, playerId);
        log.info("PlayerMessage %s exe time:%d%n".formatted(record.method().getName(), System.currentTimeMillis() - time));
    }
}
