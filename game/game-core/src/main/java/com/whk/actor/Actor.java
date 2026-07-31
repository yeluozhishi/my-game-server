package com.whk.actor;

import com.whk.actor.attribute.Attributes;
import com.whk.scene.actor.Movement;
import com.whk.towerAOI.entity.IMapObject;
import com.whk.towerAOI.entity.Point;
import com.whk.towerAOI.entity.View;
import io.protostuff.Exclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class Actor implements Serializable, IMapObject {
    private long id = 0L;
    // 属性
    private Attributes attributes = new Attributes();
    // 行为
    @Exclude
    private Behavior behavior = new Behavior();
    // 变动属性
    private Statuses statuses = new Statuses();

    private Movement movement = new Movement();

    private View view;

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public long getExp() {
        return 0;
    }

    @Override
    public int getCareer() {
        return 0;
    }

    @Override
    public int getSex() {
        return 0;
    }

    @Override
    public long getCreateTime() {
        return 0;
    }

    @Override
    public long getServerId() {
        return 0;
    }

    @Override
    public Point getPoint() {
        return movement.getPoint();
    }

    @Override
    public void setPoint(Point point) {
        movement.setPoint(point.getX(), point.getY(), point.getZ(), point.getDir());
    }

}
