package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.EventEnum;
import com.whk.listener.eventlistener.IEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 监听器容器
 */
@Getter
@Setter
@Slf4j
public class ListenerContainer {

    private EventEnum eventEnum;


    private LinkedList<AbstractEventListener<? extends IEvent>> listeners = new LinkedList<>();


    public ListenerContainer(EventEnum eventEnum) {
        this.eventEnum = eventEnum;
    }

    public <T extends IEvent> void executeEvent(T event) {
        for (AbstractEventListener<? extends IEvent> listener : listeners) {
            try {
                listener.doAction(event);
            } catch (Exception e) {
                log.error("监听器执行出错 类别：%s 信息： %s".formatted(eventEnum.getDescription(), e.getMessage()));
            }
        }
    }

    public void add(AbstractEventListener<? extends IEvent> listener) {
        listeners.add(listener);
    }
}
