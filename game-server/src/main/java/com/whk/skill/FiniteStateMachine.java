package com.whk.skill;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class FiniteStateMachine {

    private StateEnum presentState;

    HashMap<StateEnum, AbstractState> states = new HashMap<>();

    public void addState(AbstractState state){
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
