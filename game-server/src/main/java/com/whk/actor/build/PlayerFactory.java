package com.whk.actor.build;


import com.whk.actor.Player;
import com.whk.gamedb.entity.PlayerEntity;
import com.whk.module.ActorModule;
import com.whk.module.LevelModule;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PlayerFactory {

    private static final HashMap<String, Class<? extends ActorModule>> registerModules = new HashMap<>();

    public static void register() {
        register0(LevelModule.class);
    }


    public static void register0(Class<? extends ActorModule> tclass) {
        registerModules.put(tclass.getName(), tclass);
    }

    public static Player createPlayer(PlayerEntity playerEntity, String gateTopic, int gateServerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PlayerBuilder playerBuilder = new PlayerBuilder();
        playerBuilder.setPlayerEntity(playerEntity).setGateServerId(gateServerId).setGateTopic(gateTopic)
                .setRegisterModules(registerModules);
        return playerBuilder.buildPlayer();
    }

}
