package com.whk.actor.component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractComponent<IEntity> {

    private IEntity entity;

}
