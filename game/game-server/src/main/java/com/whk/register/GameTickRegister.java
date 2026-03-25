package com.whk.register;

import com.whk.SpringUtils;
import com.whk.server.GameServerManager;
import com.whk.service.player.PlayerBagService;
import com.whk.service.player.PlayerModuleService;
import com.whk.service.player.PlayerRepositoryService;
import com.whk.tick.TICK_ENUM;
import com.whk.tick.TickEvent;
import com.whk.tick.WorldTick;

public class GameTickRegister {

    public GameTickRegister() {
        WorldTick.INSTANCE.initScheduleEvent();
        oneMinute();
        onceTask();
    }

    public void oneMinute() {
        TickEvent tickEvent = new TickEvent(TICK_ENUM.SERVER_HEART_1_M);
        WorldTick.INSTANCE.addTask(tickEvent);
        //玩家数据缓存
        tickEvent.getRunnableList().add(() -> SpringUtils.getBean(PlayerBagService.class).checkCache());
        tickEvent.getRunnableList().add(() -> SpringUtils.getBean(PlayerModuleService.class).checkCache());
        tickEvent.getRunnableList().add(() -> SpringUtils.getBean(PlayerRepositoryService.class).checkCache());


    }

    public void onceTask() {
        WorldTick.INSTANCE.onceTask(() -> GameServerManager.getInstance().updateGate(), 1);
    }

}
