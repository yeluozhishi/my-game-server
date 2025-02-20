package com.whk.actor;

import com.whk.towerAOI.entity.IMapObject;
import com.whk.towerAOI.entity.Point;
import com.whk.towerAOI.entity.View;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景玩家代理对象
 */
@Getter
@Setter
public class PlayerActor extends Actor implements IMapObject {

    private IMovement movement;

    private int dateServerId;

    private String gateTopic;

    private View view;

    @Override
    public Point getPoint() {
        return movement.getPoint();
    }

    @Override
    public void setPoint(Point point) {
        movement.setPoint(point);
    }

}
