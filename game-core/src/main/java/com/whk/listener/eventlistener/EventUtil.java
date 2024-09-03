package com.whk.listener.eventlistener;

import com.whk.listener.eventlistener.listener.ListenerContainer;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.handler.IHandler;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

public enum EventUtil {

    INSTANCE;

    /**
     * 监听
     */
    private final HashMap<EventEnum, ListenerContainer> staticListener = new HashMap<>();

    private ThreadPoolExecutor eventThread;

    public <T extends IEvent> void fireEvent(EventEnum eventEnum, T event) {
        if (Objects.isNull(eventThread)) {
            eventThread = ThreadPoolManager.getInstance().getExecutor(ThreadType.EVENT_THREAD);
        }
        eventThread.execute(() -> {
            var container = staticListener.get(eventEnum);
            if (Objects.nonNull(container)) {
                container.executeEvent(event);
            }
        });
    }

    public void addListener(EventEnum eventEnum, IHandler listener) {
        var container = staticListener.computeIfAbsent(eventEnum, ListenerContainer::new);
        container.add(listener);
    }
}
