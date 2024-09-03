package com.whk.scene;

import com.whk.entity.MapDef;
import com.whk.threadpool.ThreadType;

public class SceneBuilder {

    public static AbstractScene build(MapDef mapDef) {
        DefaultScene scene = new DefaultScene(mapDef, ThreadType.SCENE_THREAD);
        scene.init();

        SceneManager.INSTANCE.addScene(scene);

        return scene;
    }

}
