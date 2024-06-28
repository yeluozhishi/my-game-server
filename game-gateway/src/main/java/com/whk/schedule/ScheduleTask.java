package com.whk.schedule;


import com.whk.script.IOtherScript;
import com.whk.service.GameGateConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.whk.classScan.ScannerClassException;
import org.whk.classScan.ScannerClassUtil;
import script.ScriptEngine;


@Component
public class ScheduleTask {

    private GameGateConnector gameGateConnector;

    @Autowired
    public void setServerConnector(GameGateConnector gameGateConnector) {
        this.gameGateConnector = gameGateConnector;
    }

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void TenMinutesTask(){
        gameGateConnector.reload();
    }


    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testTask() {

    }
}
