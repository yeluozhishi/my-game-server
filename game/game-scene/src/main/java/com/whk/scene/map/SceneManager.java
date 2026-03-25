package com.whk.scene.map;

import com.whk.scene.map.script.ISceneScript;
import lombok.Getter;
import script.ScriptHolder;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum SceneManager {
    INSTANCE;

    @Getter
    private String mapPath;

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

    public void createMainScene(String mapPath) {
        this.mapPath = mapPath;
        if (Objects.isNull(mapPath) || mapPath.isEmpty()) {
            this.mapPath = "%s/config/src/main/config/map/".formatted(System.getProperty("user.dir"));
        }
        ScriptHolder.INSTANCE.getScript(ISceneScript.class).createMainScene();
    }

    public void stop() {
        scenes.values().forEach(scene -> scene.getDriver().stop());
    }

}
