package com.whk.skill;

import com.whk.actor.Player;
import com.whk.entity.SkillDef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skill {

    Player source;

    Player target;

    SkillStage stage;

    SkillDef def;

}
