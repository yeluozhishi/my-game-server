package com.whk.listener.listener;

import com.whk.listener.EventEnum;
import com.whk.listener.event.IEvent;
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


    private LinkedList<IEventListener<?>> listeners = new LinkedList<>();


    public ListenerContainer(EventEnum eventEnum) {
        this.eventEnum = eventEnum;
    }

    public void execute(Object event) {
        for (IEventListener listener : listeners) {
            try {
                listener.dealEvent(event);
            } catch (Exception e) {
                logger.severe("监听器执行出错 leibie：%s 信息： %s".formatted(eventEnum.getDescription(), e.getMessage()));
            }
        }
    }

    public void add(IEventListener<?> listener) {
        listeners.add(listener);
    }
}
