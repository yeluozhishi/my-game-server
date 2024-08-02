package com.whk.dispatchprotocol;

import com.whk.threadpool.handler.AbstractHandler;
import com.whk.threadpool.handler.InstanceHandlerInterface;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;

/**
 * 事件
 */
@Getter
@Setter
public class UserMessageHandler extends AbstractHandler {

    private Object message;

    private long userId;


    public UserMessageHandler(Object message, long userId, InstanceHandlerInterface record) {
        super(record);
        this.message = message;
        this.userId = userId;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        getRecord().doAction(message, userId);
        System.out.println("UserMessage exeTime:" + (System.currentTimeMillis() - time));
    }
}
