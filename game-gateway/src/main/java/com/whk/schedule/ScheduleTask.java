package com.whk.schedule;


import com.whk.server.GateServerManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduleTask {

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testTask() {
        GateServerManager.getInstance().getCenterServers();
        GateServerManager.getInstance().requestServers();
    }
}
