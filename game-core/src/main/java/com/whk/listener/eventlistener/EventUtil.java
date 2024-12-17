package com.whk.listener.eventlistener;

import com.whk.listener.eventlistener.listener.AbstractEventListener;
import com.whk.listener.eventlistener.listener.ListenerContainer;

import java.util.HashMap;
import java.util.Objects;

public enum EventUtil {

    INSTANCE;

    /**
     * 监听
     */
    private final HashMap<EventEnum, ListenerContainer> staticListener = new HashMap<>();


    public <T extends IEvent> void fireEvent(EventEnum eventEnum, T event) {
        var container = staticListener.get(eventEnum);
        if (Objects.nonNull(container)) {
            container.executeEvent(event);
        }
    }

    public void addListener(EventEnum eventEnum, AbstractEventListener<? extends IEvent> listener) {
        var container = staticListener.computeIfAbsent(eventEnum, ListenerContainer::new);
        container.add(listener);
    }
}
