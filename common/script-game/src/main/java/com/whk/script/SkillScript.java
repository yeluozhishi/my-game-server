package com.whk.script;


import cn.hutool.core.util.RandomUtil;
import com.whk.actor.Player;
import com.whk.actor.component.attribute.Attribute;
import com.whk.skill.Skill;
import com.whk.skill.SkillBuilder;
import script.annotation.Script;

import java.util.logging.Logger;

import static com.whk.actor.component.attribute.AttributeTransform.PROP_10000;

@Script
public class SkillScript implements ISkillScript {

    private final Logger log = Logger.getLogger(SkillScript.class.getName());

    @Override
    public void executeScript(Skill skill) {
        switch (skill.getDef().getScript()) {
            case "hurt" -> hurt(skill);
            case "buff" -> buff(skill);
            default -> noSkillScript(skill);
        }
    }

    @Override
    public void releaseSkill(Player player, int skillId) {
        player.getMovement().getScene().addSkill(SkillBuilder.buildSkill(player, skillId, player.getId()));
    }

    private void noSkillScript(Skill skill) {
        log.severe("no skill script :%s".formatted(skill.getDef().getScript()));
    }

    /**
     * 状态
     *
     * @param skill 技能
     */
    private void buff(Skill skill) {

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
        return (long) ((targetAttribute.getDefenseBase() + noIgnoreDefenseBase) * (1.0 + ignore / PROP_10000));
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
            skill.setNextSkill(null);
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
