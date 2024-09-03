package com.whk.script;

import com.whk.skill.Skill;
import script.scriptInterface.IScript;

public interface ISkillScript extends IScript {
    
    void flySkillHit(Skill skill);

    void flySkillOutOfTime(Skill skill);

    void hitSkill(Skill skill);

}
