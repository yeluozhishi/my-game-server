package com.whk.actor;

import com.whk.actor.component.BasicInfo;
import com.whk.actor.component.PlayerTemporary;
import com.whk.actor.component.ServerInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Administrator
 */

@Getter
@Setter
public class Player {
    private long id = 0L;

    private BasicInfo basicInfo;

    private ServerInfo serverInfo;

    private PlayerTemporary temporary;
}
