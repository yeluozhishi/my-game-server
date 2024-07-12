package com.whk.actor;

import com.whk.actor.component.*;
import io.protostuff.Exclude;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Administrator
 */

@Getter
@Setter
public class Player extends Actor {
    @Exclude
    private BasicInfo basicInfo = new BasicInfo();
    @Tag(1)
    private Resource resource = new Resource();
    @Exclude
    private Bag bag = new Bag();
    @Tag(2)
    private Repository repository = new Repository();
    @Exclude
    private ServerInfo serverInfo = new ServerInfo();
    @Tag(3)
    private PlayerModule playerModule = new PlayerModule();

}
