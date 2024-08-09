package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.EventEnum;
import com.whk.listener.eventlistener.event.IEvent;
import com.whk.threadpool.DriverProcessor;
import com.whk.threadpool.HandlerFactory;
import com.whk.threadpool.handler.HandlerInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 监听器容器
 */
@Getter
@Setter
public class ListenerContainer {

    private Logger logger = Logger.getLogger(ListenerContainer.class.getName());

    private EventEnum eventEnum;


    private LinkedList<HandlerInterface> listeners = new LinkedList<>();


    public ListenerContainer(EventEnum eventEnum) {
        this.eventEnum = eventEnum;
    }

    public <T extends IEvent> void executeEvent(T event) {
        for (HandlerInterface listener : listeners) {
            try {
                DriverProcessor.INSTANCE
                        .addEventHandler(event.getOrderId(), HandlerFactory.INSTANCE.createEventHandler(event, listener));
            } catch (Exception e) {
                logger.severe("监听器执行出错 类别：%s 信息： %s".formatted(eventEnum.getDescription(), e.getMessage()));
            }
        }
    }

    public void add(HandlerInterface listener) {
        listeners.add(listener);
    }
}
