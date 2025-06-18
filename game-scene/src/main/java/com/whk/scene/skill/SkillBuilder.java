package com.whk.scene.skill;

import com.whk.ConfigCacheManager;
import com.whk.actor.PlayerActor;
import com.whk.comfig.SkillConfig;
import com.whk.entity.SkillDef;
import com.whk.scene.map.AbstractScene;

public class SkillBuilder {

    public static Skill buildSkill(PlayerActor player, int skillId, long targetId) {
        SkillConfig config = ConfigCacheManager.INSTANCE.getConfigCache(SkillConfig.class);
        PlayerActor target = ((AbstractScene) player.getMovement().getScene()).getPlayerMap().get(targetId);
        SkillDef def = config.getDef(skillId);
        Skill skill = new Skill();
        skill.setSource(player);
        skill.setTarget(target);
        skill.setDef(def);
        return skill;
    }

}
