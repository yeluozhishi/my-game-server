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
public class PlayerMessageHandler extends AbstractHandler {

    private Object message;

    private long playerId;

    public PlayerMessageHandler(Object message, long playerId, InstanceHandlerInterface record) {
        super(record);
        this.message = message;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        getRecord().doAction(message, playerId);
        System.out.println("PlayerMessage exetime:" + (System.currentTimeMillis() - time));
    }
}
