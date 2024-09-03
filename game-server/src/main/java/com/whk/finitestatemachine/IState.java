package com.whk.finitestatemachine;

public interface IState {

    StateEnum getStateEnum();

    void enter(FiniteStateMachine finiteStateMachine);

    void execute(FiniteStateMachine finiteStateMachine);

    void exit(FiniteStateMachine finiteStateMachine);
}
