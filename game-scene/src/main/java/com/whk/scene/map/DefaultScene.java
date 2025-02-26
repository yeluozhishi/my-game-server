package com.whk.scene.map;

import com.whk.entity.MapDef;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class DefaultScene extends AbstractScene{

    public DefaultScene(MapDef mapDef) {
        super(mapDef);
    }


    @Override
    public void sceneTick() {
//        log.info("%s 场景事件处理, 线程：%s".formatted(sceneId, Thread.currentThread().getName()));
    }
}
