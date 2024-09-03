package com.whk.skill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillStage {

    SkillStage next;

    boolean finish;

    void execute(Skill skill){}
}
