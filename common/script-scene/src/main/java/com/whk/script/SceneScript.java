package com.whk.script;

import com.whk.ConfigCacheManager;
import com.whk.comfig.MapConfig;
import com.whk.scene.map.AbstractScene;
import com.whk.scene.map.SceneBuilder;
import com.whk.scene.map.SceneManager;
import com.whk.scene.map.script.ISceneScript;
import script.annotation.Script;

@Script
public class SceneScript implements ISceneScript {
    @Override
    public void createMainScene() {
        MapConfig config = ConfigCacheManager.INSTANCE.getConfigCache(MapConfig.class);
        config.getHashMap().values().forEach(configDef -> {
            if (configDef.getType() == 1 && configDef.getLine() == 1) {
                AbstractScene scene = SceneBuilder.build(configDef);
                SceneManager.INSTANCE.addScene(scene);
            }
        });
    }
}
