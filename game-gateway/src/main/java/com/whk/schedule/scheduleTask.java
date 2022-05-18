package com.whk.schedule;

import com.whk.service.ServerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class scheduleTask {

    private ServerConnector serverConnector;

    @Autowired
    public void setServerConnector(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void TenMinutesTask(){
        serverConnector.reload();
    }


}
