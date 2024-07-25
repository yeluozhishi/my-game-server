package com.whk.script;

import com.whk.ConfigCacheManager;
import com.whk.SpringUtils;
import com.whk.actor.Player;
import com.whk.actor.component.attribute.RoleAttributeManager;
import com.whk.comfig.CharacterLevelConfig;
import com.whk.module.LevelModule;
import com.whk.net.SendMessageHolder;
import com.whk.service.player.PlayerModuleService;
import script.annotation.Script;

import java.util.Objects;

@Script
public class LevelScript implements ILevelScript{

    @Override
    public void levelUp(Player player){
        LevelModule levelModule = player.getPlayerModule().getModule(LevelModule.class);
        var config = ConfigCacheManager.INSTANCE.getConfigCache(CharacterLevelConfig.class).getDef(levelModule.getLevel() + 1);
        if (Objects.isNull(config)) {
            SendMessageHolder.INSTANCE.sendTips(23, player.getId());
            return;
        }
        levelModule.setLevel(config.level);
        SpringUtils.getBean(PlayerModuleService.class).update(player.getPlayerModule().updateEntity());
        RoleAttributeManager.INSTANCE.calculateModuleAndRebuild(player.getAttributes(), levelModule);
        SendMessageHolder.INSTANCE.sendTips(22, player.getId(), String.valueOf(levelModule.getLevel()));
    }


    public void addExp(){

    }
}
