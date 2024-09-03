package com.whk.finitestatemachine;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class FiniteStateMachine {

    private StateEnum presentState;

    private boolean overFlag;

    private HashMap<StateEnum, IState> states = new HashMap<>();

    public void addState(IState state){
        states.put(state.getStateEnum(), state);
    }

    public void process(){
        var state = states.get(presentState);
        state.enter(this);
        if (presentState != state.getStateEnum()) return;
        state.execute(this);
        if (presentState != state.getStateEnum()) return;
        state.exit(this);
    }

}
