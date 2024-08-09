package com.whk.skill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractState<T> implements IState{

    protected abstract StateEnum getStateEnum();
}
