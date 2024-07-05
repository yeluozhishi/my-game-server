package com.whk.actor;

import com.whk.actor.component.Bag;
import com.whk.actor.component.Repository;
import com.whk.actor.component.Resource;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Administrator
 */

@Getter
@Setter
public class Player extends Actor {

    @Tag(7)
    private Resource resource = new Resource();
    @Tag(8)
    private Bag bag = new Bag();
    @Tag(9)
    private Repository repository = new Repository();


    @Override
    public void init() {

    }

}
