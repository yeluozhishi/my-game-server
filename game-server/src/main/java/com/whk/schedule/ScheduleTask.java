package com.whk.schedule;

import com.whk.server.GameServerManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {


    @Scheduled(cron = "0 0/1 * * * ? ")
    public void task(){
        GameServerManager.getInstance().updateGate();
        GameServerManager.getInstance().requestServers();
    }

}
