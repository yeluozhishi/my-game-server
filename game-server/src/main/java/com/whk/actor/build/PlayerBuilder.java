package com.whk.actor.build;

import com.whk.SpringUtils;
import com.whk.actor.Player;
import com.whk.actor.component.Bag;
import com.whk.actor.component.PlayerModule;
import com.whk.actor.component.Repository;
import com.whk.actor.component.Resource;
import com.whk.config.GameDateConfig;
import com.whk.gamedb.entity.*;
import com.whk.module.LevelModule;
import com.whk.net.kafka.MessageInnerDecoder;
import com.whk.service.player.PlayerBagService;
import com.whk.service.player.PlayerModuleService;
import com.whk.service.player.PlayerRepositoryService;
import com.whk.service.player.PlayerResourceService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.util.Optional;

@Getter
@Setter
@Accessors(chain = true)
public class PlayerBuilder {
    Boolean createMode;

    PlayerEntity playerEntity;
    String gateTopic;

    public Player buildPlayer() {
        if (createMode) {
            return newPlayer();
        } else {
            return loadPlayer();
        }
    }

    private Player loadPlayer() {
        Player player = new Player();

        setBasicInfo(player);

        setBagInfo(player);

        setResourceInfo(player);

        setRepositoryInfo(player);

        setPlayerModuleInfo(player);

        return player;
    }

    private Player newPlayer() {
        var player = new Player();
        setBasicInfo(player);
        createPlayerRepositoryInfo(player);
        createPlayerBagInfo(player);
        createPlayerResourceInfo(player);
        createPlayerModuleInfo(player);
        return player;
    }

    private void setRepositoryInfo(Player player) {
        SpringUtils.getBean(PlayerRepositoryService.class).find(player.getId()).ifPresent(repository -> {
            var optionalData = getData(repository.getData(), Repository.class);
            optionalData.ifPresent(data -> {
                player.setRepository(data);
                player.getRepository().setEntity(repository);
            });
        });
    }

    private void setPlayerModuleInfo(Player player) {
        SpringUtils.getBean(PlayerModuleService.class).find(player.getId()).ifPresent(module -> {
            var optionalData = getData(module.getData(), PlayerModule.class);
            optionalData.ifPresent(data -> {
                player.setPlayerModule(data);
                player.getPlayerModule().setEntity(module);
            });
        });
    }

    private void setResourceInfo(Player player) {
        SpringUtils.getBean(PlayerResourceService.class).find(player.getId()).ifPresent(resource -> {
            var optionalData = getData(resource.getData(), Resource.class);
            optionalData.ifPresent(data -> {
                player.setResource(data);
                player.getResource().setEntity(resource);
            });
        });
    }

    private void setBagInfo(Player player) {
        var bagOpt = SpringUtils.getBean(PlayerBagService.class).find(player.getId());
        bagOpt.ifPresent(bag -> {
            var dataOpt = getData(bag.getBagData(), Bag.class);
            dataOpt.ifPresent(data -> {
                player.setBag(data);
                player.getBag().setEntity(bag);
            });
        });
    }


    private void createPlayerBagInfo(Player player) {
        PlayerBagEntity playerBagEntity = new PlayerBagEntity();
        playerBagEntity.setId(player.getId());
        playerBagEntity.setBagData(serialize(player.getBag()));
        SpringUtils.getBean(PlayerBagService.class).create(player.getId(), playerBagEntity);
        player.getBag().setEntity(playerBagEntity);
    }

    private void createPlayerResourceInfo(Player player) {
        PlayerResourceEntity playerResourceEntity = new PlayerResourceEntity();
        playerResourceEntity.setId(player.getId());
        playerResourceEntity.setData(serialize(player.getResource()));
        SpringUtils.getBean(PlayerResourceService.class).create(player.getId(), playerResourceEntity);
        player.getResource().setEntity(playerResourceEntity);
    }

    private void createPlayerRepositoryInfo(Player player) {
        PlayerRepositoryEntity repository = new PlayerRepositoryEntity();
        repository.setId(player.getId());
        repository.setData(serialize(player.getRepository()));
        SpringUtils.getBean(PlayerRepositoryService.class).create(player.getId(), repository);
        player.getRepository().setEntity(repository);
    }

    private void createPlayerModuleInfo(Player player) {
        PlayerModuleEntity playerModuleEntity = new PlayerModuleEntity();
        playerModuleEntity.setId(player.getId());
        playerModuleEntity.setData(serialize(player.getRepository()));
        SpringUtils.getBean(PlayerModuleService.class).create(player.getId(), playerModuleEntity);
        player.getPlayerModule().setEntity(playerModuleEntity);
    }


    private void setBasicInfo(Player player) {
        GameDateConfig config = SpringUtils.getBean(GameDateConfig.class);
        player.setId(playerEntity.getId());
        var basicInfo = player.getBasicInfo();
        basicInfo.setCareer(playerEntity.getCareer());
        basicInfo.setSex(playerEntity.getSex());

        var serverInfo = player.getServerInfo();
        serverInfo.setGateTopic(gateTopic);
        serverInfo.setServerId(config.getServer());
        serverInfo.setPresentServerId(config.getServer());
    }

    public <T> Optional<T> getData(byte[] data, Class<T> tClass) {
        return MessageInnerDecoder.INSTANCE.getProtostuffSerializeUtil().decode(data, tClass);
    }

    public byte[] serialize(Object message) {
        return MessageInnerDecoder.INSTANCE.getProtostuffSerializeUtil().encode(message).array();
    }

    public static void main(String[] args) {
        PlayerModule playerModule = new PlayerModule();
        LevelModule levelModule = new LevelModule();
        levelModule.setLevel(2);
        playerModule.getModules().put(LevelModule.class.getName(), levelModule);
        var se = MessageInnerDecoder.INSTANCE.getProtostuffSerializeUtil().encode(playerModule).array();

        var n = MessageInnerDecoder.INSTANCE.getProtostuffSerializeUtil().decode(se, PlayerModule.class).get();
        System.out.println(playerModule);
        System.out.println(n);

    }

}
