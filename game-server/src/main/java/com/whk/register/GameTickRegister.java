package com.whk.register;

import com.whk.server.GameServerManager;
import com.whk.tick.WorldTick;

public class GameTickRegister {

    public GameTickRegister() {
        WorldTick.INSTANCE.initScheduleEvent();
        oneMinute();
        onceTask();
    }



    public void oneMinute(){
    }

    public void onceTask(){
        WorldTick.INSTANCE.onceTask(() -> GameServerManager.getInstance().updateGate(false), 20);
    }

}
