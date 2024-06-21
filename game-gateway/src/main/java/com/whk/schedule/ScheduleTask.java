package com.whk.schedule;


import com.whk.script.IOtherScript;
import com.whk.service.ServerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.whk.classScan.ScannerClassException;
import org.whk.classScan.ScannerClassUtil;
import script.ScriptEngine;


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


    @Scheduled(cron = "0 0/1 * * * ? ")
    public void testTask() throws ScannerClassException {
        var l = ScannerClassUtil.INSTANCE.scanClassFile("com.whk.config", null);
        for (Class<?> s : l) {
            System.out.println(s.getName());
        }

        ScriptEngine scriptEngine = new ScriptEngine();

        scriptEngine.reload("D:\\game-script-1.0-SNAPSHOT.jar");
        scriptEngine.getScript(IOtherScript.class).run();

        scriptEngine.reload("E:\\gate-script-1.0-SNAPSHOT.jar");
        scriptEngine.getScript(IOtherScript.class).run();
    }
}
