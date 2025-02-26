package com.whk.scene.actor;

import com.whk.actor.IMovement;
import com.whk.scene.map.AbstractScene;
import com.whk.towerAOI.entity.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movement implements IMovement {

    private AbstractScene scene;

    private Point point;

}
