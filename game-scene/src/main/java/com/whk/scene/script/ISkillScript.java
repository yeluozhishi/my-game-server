package com.whk.scene.script;

import com.whk.actor.PlayerActor;
import com.whk.scene.skill.Skill;
import script.scriptInterface.IScript;

public interface ISkillScript extends IScript {

    void executeScript(Skill skill);

    void releaseSkill(PlayerActor player, int skillId);
}
