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

    private final Map<Long, com.whk.scene.AbstractScene> scenes = new ConcurrentHashMap<>();

    // mapid -> line -> scene
    private final Map<Integer, Map<Integer, com.whk.scene.AbstractScene>> scenesByCfg = new ConcurrentHashMap<>();

    public void tick() {
        scenes.values().forEach(com.whk.scene.AbstractScene::tick);
    }

    public void addScene(com.whk.scene.AbstractScene scene) {
        scenes.put(scene.getSceneId(), scene);
        scenesByCfg.computeIfAbsent(scene.getMapDef().getId(), k -> new ConcurrentHashMap<>()).put(scene.getMapDef().getLine(), scene);
    }

    public void removeScene(com.whk.scene.AbstractScene scene) {
        scenes.remove(scene.getSceneId());
        scenesByCfg.get(scene.getMapDef().getId()).remove(scene.getMapDef().getLine());
    }

    public com.whk.scene.AbstractScene getScene(long sceneId) {
        return scenes.get(sceneId);
    }

    public com.whk.scene.AbstractScene getScene(int mapId, int line) {
        return scenesByCfg.get(mapId).get(line);
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
        scenes.clear();
        scenesByCfg.clear();
    }

}
