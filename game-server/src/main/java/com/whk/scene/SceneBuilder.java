package com.whk.scene;

import com.whk.entity.MapDef;

public class SceneBuilder {

    public static AbstractScene build(MapDef mapDef) {
        DefaultScene scene = new DefaultScene(mapDef);
        scene.init();

        SceneManager.INSTANCE.addScene(scene);

        return scene;
    }

}
