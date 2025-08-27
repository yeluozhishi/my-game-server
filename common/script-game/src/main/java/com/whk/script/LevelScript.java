package com.whk.script;

import com.whk.ConfigCacheManager;
import com.whk.SpringUtils;
import com.whk.actor.Player;
import com.whk.actor.RoleAttributeManager;
import com.whk.actor.component.PlayerModule;
import com.whk.config.CharacterLevelConfig;
import com.whk.message.MESSAGE_CODE;
import com.whk.module.LevelModule;
import com.whk.net.SendMessageHolder;
import com.whk.service.player.PlayerModuleService;
import script.annotation.Script;

import java.util.Objects;

@Script
public class LevelScript implements ILevelScript {

    @Override
    public void levelUp(Player player) {
        PlayerModule playerModule = SpringUtils.getBean(PlayerModuleService.class).find(player.getId());
        LevelModule levelModule = playerModule.getModule(LevelModule.class);
        var config = ConfigCacheManager.INSTANCE.getConfigCache(CharacterLevelConfig.class).getDef(levelModule.getLevel() + 1);
        if (Objects.isNull(config)) {
            SendMessageHolder.INSTANCE.sendTips(MESSAGE_CODE.升级失败, player.getId());
            return;
        }
        levelModule.setLevel(config.level);
        playerModule.updata();
        RoleAttributeManager.INSTANCE.calculateModuleAndRebuild(player.getAttributes(), levelModule);
        SendMessageHolder.INSTANCE.sendTips(MESSAGE_CODE.升级成功, player.getId(), String.valueOf(levelModule.getLevel()));
    }


    public void addExp() {

    }
}
