package com.whk.scene;

import com.whk.entity.MapDef;
import com.whk.threadpool.ThreadType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultScene extends AbstractScene{

    private long sceneId;

    private MapDef mapDef;

    public DefaultScene(MapDef mapDef, ThreadType threadType) {
        super(threadType);
        this.mapDef = mapDef;
    }

    public void init(){
        sceneId = mapDef.getId();
    }



    @Override
    public void sceneTick() {

    }
}
