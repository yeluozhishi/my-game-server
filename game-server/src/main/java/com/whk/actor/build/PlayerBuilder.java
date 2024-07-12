package com.whk.actor.build;


import com.whk.SpringUtils;
import com.whk.actor.Player;
import com.whk.config.GameDateConfig;
import com.whk.gamedb.entity.PlayerEntity;
import com.whk.module.ActorModule;
import com.whk.module.LevelModule;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PlayerBuilder {

    private static final HashMap<String, Class<? extends ActorModule>> registerModules = new HashMap<>();


    public static void register(){
        register0(LevelModule.class);
    }



    public static void register0(Class<? extends ActorModule> tclass){
        registerModules.put(tclass.getName(), tclass);
    }

    public static Player createPlayer(PlayerEntity playerEntity, String gateTopic) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Player player = new Player();
        GameDateConfig config = SpringUtils.getBean(GameDateConfig.class);
        player.setId(playerEntity.getId());
        var basicInfo = player.getBasicInfo();
        basicInfo.setCareer(playerEntity.getCareer());
        basicInfo.setSex(playerEntity.getSex());

        var serverInfo = player.getServerInfo();
        serverInfo.setGateTopic(gateTopic);
        serverInfo.setServerId(config.getServer());
        serverInfo.setPresentServerId(config.getServer());

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
        }
    }

}
