package com.whk.scene.actor;

import com.whk.actor.IMovement;
import com.whk.scene.map.AbstractScene;
import com.whk.towerAOI.entity.Point;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Movement implements IMovement {

    private AbstractScene scene;

    private Point point;

    public void setPoint(Point point) {
        if (Objects.isNull(this.point)) {
            this.point = new Point(point.getX(), point.getY());
        }
        this.point.setX(point.getX());
        this.point.setY(point.getY());
    }
}
