package com.whk.script;

import com.whk.actor.Player;
import com.whk.skill.Skill;
import script.scriptInterface.IScript;

public interface ISkillScript extends IScript {

    void executeScript(Skill skill);

    void releaseSkill(Player player, int skillId);
}
