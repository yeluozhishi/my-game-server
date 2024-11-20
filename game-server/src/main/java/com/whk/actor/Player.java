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

    private BasicInfo basicInfo = new BasicInfo();

    private ServerInfo serverInfo = new ServerInfo();

    private Bag bag = new Bag();

    private Repository repository = new Repository();

    private PlayerModule playerModule = new PlayerModule();

}
