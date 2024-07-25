package com.whk.actor.build;


import com.whk.SpringUtils;
import com.whk.actor.Player;
import com.whk.actor.component.PlayerModule;
import com.whk.gamedb.entity.PlayerEntity;
import com.whk.module.ActorModule;
import com.whk.module.LevelModule;
import com.whk.service.player.PlayerModuleService;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerFactory {

    private static final HashMap<String, Class<? extends ActorModule>> registerModules = new HashMap<>();

    public static void register(){
        register0(LevelModule.class);
    }


    public static void register0(Class<? extends ActorModule> tclass){
        registerModules.put(tclass.getName(), tclass);
    }

    public static Player createPlayer(PlayerEntity playerEntity, String gateTopic, boolean createMode) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PlayerBuilder playerBuilder = new PlayerBuilder();
        playerBuilder.setPlayerEntity(playerEntity).setGateTopic(gateTopic).setCreateMode(createMode);
        Player player = playerBuilder.buildPlayer();
        initModule(player);
        return player;
    }

    public static void initModule(Player player) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!player.getPlayerModule().getModules().keySet().containsAll(registerModules.keySet())){
            for (Map.Entry<String, Class<? extends ActorModule>> entry : registerModules.entrySet()) {
                String key = entry.getKey();
                Class<? extends ActorModule> value = entry.getValue();
                if (!player.getPlayerModule().getModules().containsKey(key)) {
                    var obj = value.getDeclaredConstructor().newInstance();
                    player.getPlayerModule().getModules().put(key, obj);
                }
            }
            SpringUtils.getBean(PlayerModuleService.class).update(player.getPlayerModule().getEntity());
        }
    }

}
