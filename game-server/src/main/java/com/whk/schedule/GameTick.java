package com.whk.schedule;

import com.whk.server.GameServerManager;
import com.whk.tick.TICK_ENUM;
import com.whk.tick.TickEvent;
import com.whk.tick.WorldTick;

public class GameTick {

    public static void init(){
        WorldTick.INSTANCE.initScheduleEvent();
        oneMinute();
    }


    public static void oneMinute(){
        TickEvent tickEvent = new TickEvent(TICK_ENUM.SERVER_HEART_1_M);
        WorldTick.INSTANCE.addTask(tickEvent);
        tickEvent.getRunnableList().add(() -> GameServerManager.getInstance().updateGate());
        tickEvent.getRunnableList().add(() -> GameServerManager.getInstance().requestServers());
    }

}
