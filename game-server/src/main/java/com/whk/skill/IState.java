package com.whk.skill;

public interface IState {

    void enter(FiniteStateMachine finiteStateMachine);

    void execute(FiniteStateMachine finiteStateMachine);

    void exit(FiniteStateMachine finiteStateMachine);
}
