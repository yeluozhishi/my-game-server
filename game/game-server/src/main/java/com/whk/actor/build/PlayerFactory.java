package com.whk.actor.build;


import com.whk.SpringUtils;
import com.whk.actor.Player;
import com.whk.actor.component.*;
import com.whk.config.GameDateConfig;
import com.whk.db.entity.PlayerBagEntity;
import com.whk.db.entity.PlayerModuleEntity;
import com.whk.db.entity.PlayerRepositoryEntity;
import com.whk.module.ActorModule;
import com.whk.module.LevelModule;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.script.IAttributesScript;
import com.whk.service.player.PlayerBagService;
import com.whk.service.player.PlayerModuleService;
import com.whk.service.player.PlayerRepositoryService;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlayerFactory {

    private static final HashMap<String, Class<? extends ActorModule>> registerModules = new HashMap<>();

    public static void register() {
        register0(LevelModule.class);
    }


    public static void register0(Class<? extends ActorModule> tclass) {
        registerModules.put(tclass.getName(), tclass);
    }

    public static Player createPlayer(BasicInfo basicInfo, String gateTopic, int gateServerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PlayerBuilder playerBuilder = new PlayerBuilder();
        playerBuilder.setBasicInfo(basicInfo).setGateServerId(gateServerId).setGateTopic(gateTopic)
                .setRegisterModules(registerModules);
        return buildPlayer(playerBuilder);
    }

    public static void main(String[] args) {
        PlayerModule playerModule = new PlayerModule();
        LevelModule levelModule = new LevelModule();
        levelModule.setLevel(2);
        playerModule.getModules().put(LevelModule.class.getName(), levelModule);
        Player player = new Player();
        player.setId(11111L);
        var se = MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(player).array();

        var n = MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().decode(se, Player.class);
        log.info(playerModule.toString());
        log.info(n.toString());

    }


    public static Player buildPlayer(PlayerBuilder playerBuilder) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Player player = new Player();

        setBasicInfo(player, playerBuilder);

        if (!SpringUtils.getBean(PlayerBagService.class).exists(player.getId())) {
            createPlayerBagInfo(player);
        }

        if (!SpringUtils.getBean(PlayerRepositoryService.class).exists(player.getId())) {
            createPlayerRepositoryInfo(player);
        }

        PlayerModule playerModule;
        if (SpringUtils.getBean(PlayerModuleService.class).exists(player.getId())) {
            playerModule = SpringUtils.getBean(PlayerModuleService.class).find(player.getId());
        } else {
            playerModule = createPlayerModuleInfo(player);
        }
        initModule(playerModule, player);
        return player;
    }


    private static void createPlayerBagInfo(Player player) {
        Bag bag = new Bag();
        PlayerBagEntity playerBagEntity = new PlayerBagEntity();
        playerBagEntity.setId(player.getId());
        playerBagEntity.setBagData(serialize(bag));
        SpringUtils.getBean(PlayerBagService.class).updateImmediately(player.getId(), playerBagEntity);
    }


    private static void createPlayerRepositoryInfo(Player player) {
        Repository repository = new Repository();
        PlayerRepositoryEntity entity = new PlayerRepositoryEntity();
        entity.setId(player.getId());
        entity.setData(serialize(repository));
        repository.setEntity(entity);
        SpringUtils.getBean(PlayerRepositoryService.class).updateImmediately(player.getId(), entity);
    }

    private static PlayerModule createPlayerModuleInfo(Player player) {
        PlayerModule playerModule = new PlayerModule();
        PlayerModuleEntity playerModuleEntity = new PlayerModuleEntity();
        playerModuleEntity.setId(player.getId());
        playerModuleEntity.setData(serialize(playerModule));
        playerModule = SpringUtils.getBean(PlayerModuleService.class).updateImmediately(player.getId(), playerModuleEntity);
        return playerModule;
    }


    private static void setBasicInfo(Player player, PlayerBuilder playerBuilder) {
        GameDateConfig config = SpringUtils.getBean(GameDateConfig.class);
        player.setId(playerBuilder.getBasicInfo().getId());
        player.setBasicInfo(playerBuilder.getBasicInfo());

        var serverInfo = new ServerInfo();
        serverInfo.setGateTopic(playerBuilder.gateTopic);
        serverInfo.setGateServerId(playerBuilder.getGateServerId());
        serverInfo.setServerId(config.getServer());
        serverInfo.setPresentServerId(config.getServer());
        player.setServerInfo(serverInfo);
    }

    public static byte[] serialize(Object message) {
        return MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(message).array();
    }

    public static void initModule(PlayerModule module, Player player) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!module.getModules().keySet().containsAll(registerModules.keySet())) {
            for (Map.Entry<String, Class<? extends ActorModule>> entry : registerModules.entrySet()) {
                String key = entry.getKey();
                Class<? extends ActorModule> value = entry.getValue();
                if (!module.getModules().containsKey(key)) {
                    var obj = value.getDeclaredConstructor().newInstance();
                    module.getModules().put(key, obj);
                }
            }
            module.update();
        }
        ScriptHolder.INSTANCE.getScript(IAttributesScript.class).fromModuleBuildAttribute(module, player.getAttributes());
    }

}
