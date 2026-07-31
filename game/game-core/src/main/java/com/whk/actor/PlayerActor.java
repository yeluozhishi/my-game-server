package com.whk.actor;

import com.whk.towerAOI.entity.View;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景玩家代理对象
 */
@Getter
@Setter
public class PlayerActor extends Actor {

    private int gateServerId;

    private int dateServerId;

    private String gateTopic;

    private View view;

    private String name;
}
