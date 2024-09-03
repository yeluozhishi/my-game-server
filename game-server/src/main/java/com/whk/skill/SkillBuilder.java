package com.whk.skill;

import com.whk.ConfigCacheManager;
import com.whk.actor.Player;
import com.whk.comfig.SkillConfig;
import com.whk.entity.SkillDef;

public class SkillBuilder {

    public static Skill buildSkill(Player player, int skillId, long target) {
        SkillConfig config = ConfigCacheManager.INSTANCE.getConfigCache(SkillConfig.class);
        SkillDef def = config.getDef(skillId);
        Skill skill = new Skill();
        skill.setSource(player);
        skill.setTarget(player.getMovement().getScene().getPlayerMap().get(target));
        skill.setDef(def);
        SkillStage stage = new SkillStage();
        skill.setStage(stage);
        return skill;
    }
}
