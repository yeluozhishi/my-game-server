package com.whk.scene.skill;

import com.whk.ConfigCacheManager;
import com.whk.actor.PlayerActor;
import com.whk.comfig.SkillConfig;
import com.whk.entity.SkillDef;
import com.whk.scene.map.AbstractScene;

import java.util.Objects;

public class SkillBuilder {

    public static Skill buildSkill(PlayerActor player, int skillId, long targetId) {
        SkillConfig config = ConfigCacheManager.INSTANCE.getConfigCache(SkillConfig.class);
        PlayerActor target = ((AbstractScene) player.getMovement().getScene()).getPlayerMap().get(targetId);
        SkillDef def = config.getDef(skillId);
        Skill skillHead = new Skill();
        skillHead.setSource(player);
        skillHead.setTarget(target);
        skillHead.setDef(def);
        addSubSkill(skillHead, config, player, target);
        return skillHead;
    }

    private static void addSubSkill(Skill skill, SkillConfig config, PlayerActor player, PlayerActor target){
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
