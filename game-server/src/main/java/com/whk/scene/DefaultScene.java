package com.whk.scene;

import com.whk.entity.MapDef;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;

@Getter
@Setter
public class DefaultScene extends AbstractScene{

    private Logger logger = Logger.getLogger(DefaultScene.class.getName());

    private long sceneId;

    private MapDef mapDef;

    public DefaultScene(MapDef mapDef) {
        this.mapDef = mapDef;
        sceneId = mapDef.getId();
    }

    public void init(){
        sceneId = mapDef.getId();
    }



    @Override
    public void sceneTick() {
//        logger.info("%d 场景事件处理, 线程：%s".formatted(sceneId, Thread.currentThread().getName()));
    }
}
