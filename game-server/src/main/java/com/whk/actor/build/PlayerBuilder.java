package com.whk.actor.build;

import com.whk.SpringUtils;
import com.whk.actor.Player;
import com.whk.actor.component.Bag;
import com.whk.actor.component.PlayerModule;
import com.whk.actor.component.Repository;
import com.whk.config.GameDateConfig;
import com.whk.gamedb.entity.PlayerBagEntity;
import com.whk.gamedb.entity.PlayerEntity;
import com.whk.gamedb.entity.PlayerModuleEntity;
import com.whk.gamedb.entity.PlayerRepositoryEntity;
import com.whk.module.ActorModule;
import com.whk.module.LevelModule;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.script.IAttributesScript;
import com.whk.service.player.PlayerBagService;
import com.whk.service.player.PlayerModuleService;
import com.whk.service.player.PlayerRepositoryService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@Slf4j
public class PlayerBuilder {
    PlayerEntity playerEntity;

    String gateTopic;

    int gateServerId;

    HashMap<String, Class<? extends ActorModule>> registerModules;

    public Player buildPlayer() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Player player = new Player();

        setBasicInfo(player);

        if (!SpringUtils.getBean(PlayerBagService.class).existsComponent(player.getId())) {
            createPlayerBagInfo(player);
        }

        if (!SpringUtils.getBean(PlayerRepositoryService.class).existsComponent(player.getId())) {
            createPlayerRepositoryInfo(player);
        }

        PlayerModule playerModule;
        if (SpringUtils.getBean(PlayerModuleService.class).existsComponent(player.getId())) {
            playerModule = SpringUtils.getBean(PlayerModuleService.class).findComponent(player.getId());
        } else {
            playerModule = createPlayerModuleInfo(player);
        }
        initModule(playerModule, player);
        return player;
    }


    private void createPlayerBagInfo(Player player) {
        Bag bag = new Bag();
        PlayerBagEntity playerBagEntity = new PlayerBagEntity();
        playerBagEntity.setId(player.getId());
        playerBagEntity.setBagData(serialize(bag));
        bag.setEntity(playerBagEntity);
        SpringUtils.getBean(PlayerBagService.class).createComponent(player.getId(), bag);
    }


    private void createPlayerRepositoryInfo(Player player) {
        Repository repository = new Repository();
        PlayerRepositoryEntity entity = new PlayerRepositoryEntity();
        entity.setId(player.getId());
        entity.setData(serialize(repository));
        repository.setEntity(entity);
        SpringUtils.getBean(PlayerRepositoryService.class).createComponent(player.getId(), repository);
    }

    private PlayerModule createPlayerModuleInfo(Player player) {
        PlayerModule playerModule = new PlayerModule();
        PlayerModuleEntity playerModuleEntity = new PlayerModuleEntity();
        playerModuleEntity.setId(player.getId());
        playerModuleEntity.setData(serialize(playerModule));
        playerModule.setEntity(playerModuleEntity);
        SpringUtils.getBean(PlayerModuleService.class).createComponent(player.getId(), playerModule);
        return playerModule;
    }


    private void setBasicInfo(Player player) {
        GameDateConfig config = SpringUtils.getBean(GameDateConfig.class);
        player.setId(playerEntity.getId());
        var basicInfo = player.getBasicInfo();
        basicInfo.setCareer(playerEntity.getCareer());
        basicInfo.setSex(playerEntity.getSex());

        var serverInfo = player.getServerInfo();
        serverInfo.setGateTopic(gateTopic);
        serverInfo.setGateServerId(getGateServerId());
        serverInfo.setServerId(config.getServer());
        serverInfo.setPresentServerId(config.getServer());
    }

    public byte[] serialize(Object message) {
        return MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(message).array();
    }

    public void initModule(PlayerModule module, Player player) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!module.getModules().keySet().containsAll(registerModules.keySet())) {
            for (Map.Entry<String, Class<? extends ActorModule>> entry : registerModules.entrySet()) {
                String key = entry.getKey();
                Class<? extends ActorModule> value = entry.getValue();
                if (!module.getModules().containsKey(key)) {
                    var obj = value.getDeclaredConstructor().newInstance();
                    module.getModules().put(key, obj);
                }
            }
            SpringUtils.getBean(PlayerModuleService.class).updateComponent(module.getId(), module);
        }
        ScriptHolder.INSTANCE.getScript(IAttributesScript.class).fromModuleBuildAttribute(module, player.getAttributes());
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

}
