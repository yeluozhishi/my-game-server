package com.whk.actor;

import com.whk.actor.component.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Administrator
 */

@Getter
@Setter
public class Player extends Actor {

    private BasicInfo basicInfo;

    private ServerInfo serverInfo;

}
