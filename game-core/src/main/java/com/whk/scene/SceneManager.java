package com.whk.scene;

import java.util.concurrent.ConcurrentHashMap;

public class SceneManager {

    private ConcurrentHashMap<String, SceneInterface> scenes = new ConcurrentHashMap<>();

    public void tick(){
        scenes.values().forEach(scene -> {

        });
    }
}
