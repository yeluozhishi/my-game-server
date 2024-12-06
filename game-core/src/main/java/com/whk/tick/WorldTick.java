package com.whk.tick;

import com.whk.threadpool.ThreadType;
import com.whk.threadpool.ThreadPoolManager;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum WorldTick {
    INSTANCE;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public final Set<TICK_ENUM> tickTasks = new HashSet<>();

    public void initScheduleEvent() {
        if (Objects.isNull(scheduledThreadPoolExecutor)) {
            scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ThreadPoolManager.getInstance().getExecutor(ThreadType.SCHEDULED_THREAD);
        }
    }

    public void addTask(TickEvent tickEvent) {
        if (tickTasks.contains(tickEvent.getTickEnum())) {
            throw new RuntimeException("already have tick task : %s".formatted(tickEvent.getTickEnum().name()));
        }
        scheduledThreadPoolExecutor.scheduleAtFixedRate(tickEvent, 1000, tickEvent.getTickEnum().getDiff(), TimeUnit.MILLISECONDS);
    }

    public void onceTask(Runnable runnable, long delay){
        scheduledThreadPoolExecutor.schedule(runnable, delay, TimeUnit.SECONDS);
    }
}
