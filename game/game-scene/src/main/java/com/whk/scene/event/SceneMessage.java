package com.whk.scene.event;

import com.whk.dispatchprotocol.PlayerMessageRecord;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
@Slf4j
public class SceneMessage extends AbstractSceneEvent {

    private Object message;

    private PlayerMessageRecord record;

    private long playerId;


    public SceneMessage(String sceneId, Object message, long playerId, PlayerMessageRecord record) {
        super(sceneId);
        this.message = message;
        this.record = record;
        this.playerId = playerId;
    }

    @Override
    public void doAction() {

    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        try {
            record.method().invoke(record.clazz(), message, playerId);
            log.info("SceneMessage %s exe time:%d%n".formatted(record.method().getName(), System.currentTimeMillis() - time));
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException exception = (InvocationTargetException) e;
            Throwable throwable = exception.getTargetException();
            log.error("SceneMessage stack: ", throwable);
        }
    }

}
