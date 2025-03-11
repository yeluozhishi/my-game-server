package com.whk.scene.map;

import com.whk.scene.map.script.ISceneScript;
import script.ScriptHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum SceneManager {
    INSTANCE;

    private final Map<String, AbstractScene> scenes = new ConcurrentHashMap<>();

    public void tick() {
        scenes.values().forEach(AbstractScene::tick);
    }

    public void addScene(AbstractScene scene) {
        scenes.put(scene.getSceneId(), scene);
    }

    public AbstractScene getScene(String sceneId) {
        return scenes.get(sceneId);
    }

    public void createMainScene() {
        ScriptHolder.INSTANCE.getScript(ISceneScript.class).createMainScene();
    }

    public void stop() {
        scenes.values().forEach(scene -> scene.getDriver().stop());
    }
}
