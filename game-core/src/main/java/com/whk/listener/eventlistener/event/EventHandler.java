package com.whk.listener.eventlistener.event;

import com.whk.threadpool.handler.AbstractHandler;
import com.whk.threadpool.handler.HandlerInterface;
import lombok.Getter;
import lombok.Setter;

/**
 * 事件
 */
@Getter
@Setter
public class EventHandler extends AbstractHandler {

    private IEvent event;


    public EventHandler(IEvent event, HandlerInterface record) {
        super(record);
        this.event = event;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        getRecord().doAction(event);
        System.out.println("PlayerMessage exetime:" + (System.currentTimeMillis() - time));
    }
}
