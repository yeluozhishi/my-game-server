package com.whk.schedule;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduleTask {


    @Scheduled(cron = "0 0/1 * * * ? ")
    public void TenMinutesTask(){

    }


    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testTask() {

//        UserMgr.INSTANCE.userLogin();

    }
}
