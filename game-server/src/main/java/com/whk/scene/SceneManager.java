package com.whk.scene;

import com.whk.ConfigCacheManager;
import com.whk.comfig.MapConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum SceneManager {
    INSTANCE;

    private final Map<Long, AbstractScene> scenes = new ConcurrentHashMap<>();

    public void tick() {
        scenes.values().forEach(AbstractScene::tick);
    }

    public void addScene(AbstractScene scene) {
        scenes.put(scene.getSceneId(), scene);
    }

    public AbstractScene getScene(Long sceneId) {
        return scenes.get(sceneId);
    }

    public void createMainScene(){
        MapConfig config = ConfigCacheManager.INSTANCE.getConfigCache(MapConfig.class);
        SceneBuilder.build(config.getDef(1008));
    }
}
