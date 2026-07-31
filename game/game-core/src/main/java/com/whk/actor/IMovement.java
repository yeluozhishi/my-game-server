package com.whk.actor;

import com.whk.scene.SceneInterface;
import com.whk.towerAOI.entity.Point;

public interface IMovement {
    SceneInterface getScene();

    Point getPoint();

    void setPoint(float x,float y,float z,float dir);
}
