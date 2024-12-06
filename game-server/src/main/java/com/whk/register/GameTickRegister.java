package com.whk.register;

import com.whk.scene.SceneManager;
import com.whk.server.GameServerManager;
import com.whk.tick.TICK_ENUM;
import com.whk.tick.TickEvent;
import com.whk.tick.WorldTick;

public class GameTickRegister {

    public GameTickRegister() {
        WorldTick.INSTANCE.initScheduleEvent();
        sceneTick();
        oneMinute();
        onceTask();
    }

    public void sceneTick(){
        TickEvent tickEvent = new TickEvent(TICK_ENUM.SERVER_HEART_500_MILLIS);
        WorldTick.INSTANCE.addTask(tickEvent);
        tickEvent.getRunnableList().add(SceneManager.INSTANCE::tick);

    }

    public void oneMinute(){
//        TickEvent tickEvent = new TickEvent(TICK_ENUM.SERVER_HEART_1_M);
//        WorldTick.INSTANCE.addTask(tickEvent);
    }

    public void onceTask(){
        WorldTick.INSTANCE.onceTask(() -> GameServerManager.getInstance().updateGate(false), 20);
    }

}
