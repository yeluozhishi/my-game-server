package com.whk.scene.register;

import com.whk.scene.map.SceneManager;
import com.whk.scene.server.SceneServerManager;
import com.whk.tick.TICK_ENUM;
import com.whk.tick.TickEvent;
import com.whk.tick.WorldTick;

public class SceneTickRegister {

    public SceneTickRegister() {
        sceneTick();
        onceTask();
    }

    public void sceneTick(){
        WorldTick.INSTANCE.initScheduleEvent();
        TickEvent tickEvent = new TickEvent(TICK_ENUM.SERVER_HEART_1_S);
        WorldTick.INSTANCE.addTask(tickEvent);
        tickEvent.getRunnableList().add(SceneManager.INSTANCE::tick);
    }

    public void onceTask(){
        WorldTick.INSTANCE.onceTask(() -> SceneServerManager.getInstance().updateGate(false), 1);
    }
}
