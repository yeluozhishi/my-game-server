package com.whk.scene.map.script;

import com.whk.actor.PlayerActor;
import script.scriptInterface.IScript;

public interface ISceneScript extends IScript {
    void createMainScene();

    void playerEnterScene(PlayerActor actor, String sceneId);
}
