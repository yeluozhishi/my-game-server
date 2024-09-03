package com.whk.threadpool.handler;

import lombok.Getter;
import lombok.Setter;

/**
 * 事件
 */
@Getter
@Setter
public class PlayerMessageHandler extends AbstractHandler {

    private Object message;

    private long playerId;

    public PlayerMessageHandler(Object message, long playerId, IHandler record) {
        super(record);
        this.message = message;
        this.playerId = playerId;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        getRecord().doAction(message, playerId);
        System.out.printf("PlayerMessage exetime:%d%n", System.currentTimeMillis() - time);
    }
}
