package com.whk.skill.skillstate;

import com.whk.finitestatemachine.ArgsState;
import com.whk.finitestatemachine.FiniteStateMachine;
import com.whk.skill.Skill;
import com.whk.skill.StateEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillStart implements ArgsState<Skill> {

    private Skill skill;

    public SkillStart(Skill skill) {
        this.skill = skill;
    }

    @Override
    public StateEnum getStateEnum() {
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
