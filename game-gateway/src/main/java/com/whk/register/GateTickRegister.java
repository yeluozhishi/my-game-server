package com.whk.register;


import com.whk.server.GateServerManager;
import com.whk.tick.WorldTick;


public class GateTickRegister {

    public GateTickRegister() {
        WorldTick.INSTANCE.initScheduleEvent();
        onceTask();
    }

    public void onceTask() {
        WorldTick.INSTANCE.onceTask(() -> GateServerManager.getInstance().getCenterServers(), 20);
    }


}
