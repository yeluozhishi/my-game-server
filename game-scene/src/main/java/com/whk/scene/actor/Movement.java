package com.whk.scene.actor;

import com.whk.actor.IMovement;
import com.whk.scene.map.AbstractScene;
import com.whk.towerAOI.entity.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movement implements IMovement {
    private int x;
    private int y;
    private int z;

    private AbstractScene scene;

    private Point point;

    public boolean inPlace(int x, int y, int z, int scope){
        return scope == Math.sqrt(Math.pow((this.x - x), 2) + Math.pow((this.y + y), 2));
    }

}
