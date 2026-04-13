package com.whk.script;

import com.whk.SpringUtils;
import com.whk.actor.PlayerMgr;
import com.whk.actor.RoleAttributeManager;
import com.whk.actor.component.PlayerModule;
import com.whk.config.CharacterLevelConfig;
import com.whk.message.MESSAGE_CODE;
import com.whk.module.LevelModule;
import com.whk.net.GameMessageUtil;
import com.whk.protobuf.message.PlayerInfoProto;
import com.whk.service.player.PlayerModuleService;
import script.annotation.Script;

import java.util.Objects;

@Script
public class LevelScript implements ILevelScript {

    @Override
    public void levelUp(long playerId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        PlayerModule playerModule = SpringUtils.getBean(PlayerModuleService.class).find(player.getId());
        if (Objects.isNull(playerModule)) return;
        LevelModule levelModule = playerModule.getModule(LevelModule.class);
        var config = CharacterLevelConfig.getInstance().getDef(levelModule.getLevel() + 1);
        if (Objects.isNull(config)) {
            GameMessageUtil.getInstance().sendTips(MESSAGE_CODE.升级失败, player.getId());
            return;
        }
        levelModule.setLevel(config.level);
        playerModule.update();
        RoleAttributeManager.INSTANCE.calculateModuleAndRebuild(player.getAttributes(), levelModule);

        PlayerInfoProto.ResLevelUp.Builder builder = PlayerInfoProto.ResLevelUp.newBuilder();
        builder.setLevel(levelModule.getLevel());
        GameMessageUtil.getInstance().sendMessage(builder.build(), player.getId());
    }

}
