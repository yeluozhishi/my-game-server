package com.whk.listener;

import com.whk.listener.event.IEvent;
import com.whk.listener.listener.IEventListener;
import com.whk.listener.listener.ListenerContainer;

import java.util.HashMap;
import java.util.Objects;

public enum EventUtil {

    INSTANCE;

    /**
     * 静态监听
     */
    private final HashMap<EventEnum, ListenerContainer> staticListener = new HashMap<>();


    public  void fireEvent(EventEnum eventEnum, Object event){
        var container = staticListener.get(eventEnum);
        if (Objects.nonNull(container)) {
            container.execute(event);
        }
    }

    public <T extends IEvent> void  addListener(EventEnum eventEnum, IEventListener<T> listener){
        var container = staticListener.putIfAbsent(eventEnum, new ListenerContainer(eventEnum));
        container.add(listener);
    }
}
