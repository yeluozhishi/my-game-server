package com.whk.register;


import com.whk.server.GateServerManager;
import com.whk.tick.WorldTick;


public class GateTickRegister {

    public GateTickRegister() {
        WorldTick.INSTANCE.initScheduleEvent();
        oneMinute();
        onceTask();
    }


    public void oneMinute() {

    }

    public void onceTask(){
        WorldTick.INSTANCE.onceTask(() -> GateServerManager.getInstance().getCenterServers(), 20);
    }


}
