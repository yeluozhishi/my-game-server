package com.whk.threadpool.event;

import com.whk.threadpool.dispatchprotocol.InstanceHandlerInterface;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;

/**
 * 事件
 */
@Getter
@Setter
public class PlayerEventHandler extends AbstractEventHandler {

    private Object message;

    private long playerId;

    public PlayerEventHandler(Object message, long playerId, InstanceHandlerInterface record) {
        super(record);
        this.message = message;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        try {
            getRecord().doAction(message, playerId);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
