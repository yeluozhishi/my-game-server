package com.whk.script;


import cn.hutool.core.util.RandomUtil;
import com.whk.actor.PlayerActor;
import com.whk.actor.attribute.Attribute;
import com.whk.actor.attribute.AttributeTransform;
import com.whk.scene.map.AbstractScene;
import com.whk.scene.script.ISkillScript;
import com.whk.scene.skill.Skill;
import com.whk.scene.skill.SkillBuilder;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;
import script.annotation.Script;

@Script
@Slf4j
public class SkillScript implements ISkillScript {

    @Override
    public void executeScript(Skill skill) {
        switch (skill.getDef().getScript()) {
            case "hurt" -> hurt(skill);
            case "addBuff" -> buff(skill);
            case "hurtBuff" -> hurtBuff(skill);
            default -> noSkillScript(skill);
        }
    }

    @Override
    public void releaseSkill(PlayerActor player, int skillId, long targetId) {
        if (player.getStatuses().isDeath()) return;
        Skill skill = SkillBuilder.buildSkill(player, skillId, targetId);
        executeScript(skill);
    }

    private void noSkillScript(Skill skill) {
        log.error("no skill script :%s".formatted(skill.getDef().getScript()));
    }

    /**
     * 状态
     *
     * @param skill 技能
     */
    private void buff(Skill skill) {

    }

    private void hurtBuff(Skill skill) {
    }

    public long getRandomAttack(Attribute attribute) {
        if (attribute.getMinimumPhysBaseDmg() >= attribute.getMaximumPhysBaseDmg()) {
            return attribute.getMinimumPhysBaseDmg();
        } else {
            return RandomUtil.randomLong(attribute.getMinimumPhysBaseDmg(), attribute.getMaximumPhysBaseDmg());
        }
    }

    public long getDefence(Attribute sourceAttribute, Attribute targetAttribute) {
        int ignore = sourceAttribute.getDefenseIgnoreChance() - targetAttribute.getDefenseIgnoreChanceResistance();
        int noIgnoreDefenseBase = targetAttribute.getNoIgnoreDefenseBase();
        return (long) ((targetAttribute.getDefenseBase() + noIgnoreDefenseBase) * (1.0 + ignore / AttributeTransform.PROP_10000));
    }

    /**
     * 伤害
     *
     * @param skill 技能
     */
    private void hurt(Skill skill) {
        var source = skill.getSource();
        var target = skill.getTarget();
        if (target.getStatuses().isDeath()) {
            skill.setFinish(true);
            return;
        }
        long attack = getRandomAttack(source.getAttributes().getFinalAttribute());
        long defence = getDefence(source.getAttributes().getFinalAttribute(), target.getAttributes().getFinalAttribute());
        attack = Math.max(0, attack - defence);
        target.getStatuses().setHp(Math.max(target.getStatuses().getHp() - attack, 0));
        if (target.getStatuses().getHp() == 0) {
            target.getStatuses().setDeath(true);
        }
        skill.setFinish(true);
    }
}
