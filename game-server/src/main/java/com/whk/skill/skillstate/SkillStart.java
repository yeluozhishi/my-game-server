package com.whk.skill.skillstate;

import com.whk.skill.AbstractState;
import com.whk.skill.FiniteStateMachine;
import com.whk.skill.Skill;
import com.whk.skill.StateEnum;

public class SkillStart extends AbstractState<Skill> {

    private Skill skill;

    public SkillStart(Skill skill) {
        this.skill = skill;
    }

    @Override
    protected StateEnum getStateEnum() {
        return SkillStateEnum.START;
    }


    @Override
    public void enter(FiniteStateMachine finiteStateMachine) {

    }

    @Override
    public void execute(FiniteStateMachine finiteStateMachine) {

    }

    @Override
    public void exit(FiniteStateMachine finiteStateMachine) {

    }
}
