package com.whk.schedule;


import com.whk.script.scriptInterface.IOtherScript;
import com.whk.script.ScriptEngine;
import com.whk.service.ServerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduleTask {

    private ServerConnector serverConnector;

    @Autowired
    public void setServerConnector(ServerConnector serverConnector) {
        this.serverConnector = serverConnector;
    }

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void TenMinutesTask(){
        serverConnector.reload();
    }


//    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testTask(){
        ScriptEngine scriptEngine = new ScriptEngine();

        scriptEngine.reload("D:\\game-script-1.0-SNAPSHOT.jar");
        scriptEngine.getScript(IOtherScript.class).run();

        scriptEngine.reload("E:\\game-script-1.0-SNAPSHOT.jar");
        scriptEngine.getScript(IOtherScript.class).run();
    }
}
