package com.whk.threadpool.event;

import com.whk.net.dispatchprotocol.MessageHandlerRecord;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;

/**
 * 事件
 */
@Getter
@Setter
public class EventHandler extends AbstractEventHandler{

    private Object message;



    public EventHandler(Object message, MessageHandlerRecord record) {
        super(record);
        this.message = message;
    }

    @Override
    public void run() {
        try {
            getRecord().doAction(message);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
