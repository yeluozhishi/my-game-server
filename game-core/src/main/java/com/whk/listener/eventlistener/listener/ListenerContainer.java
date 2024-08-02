package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.EventEnum;
import com.whk.listener.eventlistener.event.IEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 监听器容器
 */
@Getter
@Setter
public class ListenerContainer{

    private Logger logger = Logger.getLogger(ListenerContainer.class.getName());

    private EventEnum eventEnum;


    private LinkedList<IListener> listeners = new LinkedList<>();


    public ListenerContainer(EventEnum eventEnum) {
        this.eventEnum = eventEnum;
    }

    public <T extends IEvent> void execute(T event) {
        for (IListener listener : listeners) {
            try {
                listener.eventProcess(event);
            } catch (Exception e) {
                logger.severe("监听器执行出错 类别：%s 信息： %s".formatted(eventEnum.getDescription(), e.getMessage()));
            }
        }
    }

    public <T extends IEvent> void add(IEventListener<T> listener) {
        listeners.add(listener);
    }
}
