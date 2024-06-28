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
public class UserEventHandler extends AbstractEventHandler {

    private Object message;

    private long userId;


    public UserEventHandler(Object message, long userId, InstanceHandlerInterface record) {
        super(record);
        this.message = message;
        this.userId = userId;
    }

    @Override
    public void run() {
        try {
            getRecord().doAction(message, userId);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
