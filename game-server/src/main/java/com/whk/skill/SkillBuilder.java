package com.whk.skill;

import com.whk.ConfigCacheManager;
import com.whk.actor.Player;
import com.whk.comfig.SkillConfig;
import com.whk.entity.SkillDef;

import java.util.Objects;

public class SkillBuilder {

    public static Skill buildSkill(Player player, int skillId, long targetId) {
        SkillConfig config = ConfigCacheManager.INSTANCE.getConfigCache(SkillConfig.class);
        Player target = player.getMovement().getScene().getPlayerMap().get(targetId);
        SkillDef def = config.getDef(skillId);
        Skill skillHead = new Skill();
        skillHead.setSource(player);
        skillHead.setTarget(target);
        skillHead.setDef(def);
        addSubSkill(skillHead, config, player, target);
        return skillHead;
    }

    private static void addSubSkill(Skill skill, SkillConfig config, Player player, Player target){
        SkillDef def = config.getDef(skill.getDef().subSkill);
        if (Objects.nonNull(def)){
            Skill nextSkill = new Skill();
            nextSkill.setSource(player);
            nextSkill.setTarget(target);
            nextSkill.setDef(def);
            skill.setNextSkill(nextSkill);
            addSubSkill(nextSkill, config, player, target);
        }
    }
}
