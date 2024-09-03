package com.whk.actor.component;

import com.whk.scene.AbstractScene;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movement {
    private int x;
    private int y;
    private int z;

    private AbstractScene scene;

    public boolean inPlace(int x, int y, int z, int scope){
        return scope == Math.sqrt(Math.pow((this.x - x), 2) + Math.pow((this.y + y), 2));
    }
}
