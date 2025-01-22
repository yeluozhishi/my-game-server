package com.whk.actor;

import lombok.Getter;
import lombok.Setter;

/**
 * 场景玩家代理对象
 */
@Getter
@Setter
public class PlayerActor extends Actor{

    private IMovement movement;

    private int dateServerId;

    private String gateTopic;

}
