package com.whk.finitestatemachine;

import com.whk.finitestatemachine.FiniteStateMachine;
import com.whk.skill.StateEnum;

public interface IState {

    StateEnum getStateEnum();

    void enter(FiniteStateMachine finiteStateMachine);

    void execute(FiniteStateMachine finiteStateMachine);

    void exit(FiniteStateMachine finiteStateMachine);
}
