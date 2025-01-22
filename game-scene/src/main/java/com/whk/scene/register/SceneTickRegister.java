package com.whk.scene.register;

import com.whk.scene.map.SceneManager;
import com.whk.tick.TICK_ENUM;
import com.whk.tick.TickEvent;
import com.whk.tick.WorldTick;

public class SceneTickRegister {

    public SceneTickRegister() {
        sceneTick();
    }

    public void sceneTick(){
        TickEvent tickEvent = new TickEvent(TICK_ENUM.SERVER_HEART_500_MILLIS);
        WorldTick.INSTANCE.addTask(tickEvent);
        tickEvent.getRunnableList().add(SceneManager.INSTANCE::tick);

    }
}
