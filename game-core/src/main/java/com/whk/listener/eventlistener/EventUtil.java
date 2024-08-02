package com.whk.listener.eventlistener;

import com.whk.listener.eventlistener.event.IEvent;
import com.whk.listener.eventlistener.listener.ListenerContainer;
import com.whk.listener.eventlistener.listener.LoginListener;
import com.whk.threadpool.TheadType;
import com.whk.threadpool.ThreadPoolManager;

import java.util.HashMap;
import java.util.Objects;

public enum EventUtil {

    INSTANCE;

    /**
     * 静态监听
     */
    private final HashMap<EventEnum, ListenerContainer> staticListener = new HashMap<>();

    public <T extends IEvent> void fireEvent(EventEnum eventEnum, T event){
        var container = staticListener.get(eventEnum);
        if (Objects.nonNull(container)) {
            ThreadPoolManager.getInstance().getExecutor(TheadType.EVENT_THREAD).execute(() -> container.execute(event));
        }
    }

    public void  addListener(EventEnum eventEnum, LoginListener listener){
        var container = staticListener.computeIfAbsent(eventEnum, ListenerContainer::new);
        container.add(listener);
    }
}
