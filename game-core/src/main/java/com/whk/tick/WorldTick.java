package com.whk.tick;

import com.whk.threadpool.ThreadType;
import com.whk.threadpool.ThreadPoolManager;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum WorldTick {
    INSTANCE;

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public Map<TICK_ENUM, TickEvent> tickTasks = new HashMap<>();

    public void initScheduleEvent() {
        if (Objects.isNull(scheduledThreadPoolExecutor)) {
            scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ThreadPoolManager.getInstance().getExecutor(ThreadType.SCHEDULED_THREAD);
        }
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::runTask, 1000, 100, TimeUnit.MILLISECONDS);
    }

    public void addTask(TickEvent tickEvent) {
        if (tickTasks.containsKey(tickEvent.getTickEnum())) {
            throw new RuntimeException("already have tick task : %s".formatted(tickEvent.getTickEnum().name()));
        }
        tickTasks.put(tickEvent.getTickEnum(), tickEvent);
    }


    private void runTask(){
        long now = System.currentTimeMillis();
        tickTasks.values().forEach(fireEvent -> fireEvent.fireEvent(now));
    }

}
