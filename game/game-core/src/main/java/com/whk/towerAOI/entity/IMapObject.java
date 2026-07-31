package com.whk.towerAOI.entity;

import com.whk.actor.IMovement;

/**
 * 地图中的对象
 */
public interface IMapObject {
    long getId();

    Point getPoint();

    void setPoint(Point point);

    View getView();

    String getName();

    int getLevel();

    long getExp();

    int getCareer();

    int getSex();

    long getCreateTime();

    long getServerId();

    IMovement getMovement();
}
