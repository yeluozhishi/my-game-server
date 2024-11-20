package com.whk.skill;

import com.whk.actor.Actor;
import com.whk.actor.Player;
import com.whk.entity.SkillDef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skill {

    Actor source;

    Actor target;

    boolean finish;

    SkillDef def;

    Skill nextSkill;
}
