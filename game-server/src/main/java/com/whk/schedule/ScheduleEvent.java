package com.whk.schedule;

import com.whk.scene.SceneManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.ThreadPoolManager;

import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum ScheduleEvent {
    INSTANCE;

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public void initScheduleEvent() {
        if (Objects.isNull(scheduledThreadPoolExecutor)) {
            scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) ThreadPoolManager.getInstance().getExecutor(ThreadType.SCHEDULED_THREAD);
        }
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::scene, 1000, 100, TimeUnit.MILLISECONDS);
    }

    public void scene() {
        SceneManager.INSTANCE.tick();
    }
}
