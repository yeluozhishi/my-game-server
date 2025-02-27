package com.whk.scene.map.script;

import com.whk.actor.PlayerActor;
import script.scriptInterface.IScript;

public interface ISceneScript extends IScript {
    void createMainScene();

    void pushDataAndEnterScene(PlayerActor actor, String sceneId);

    void playerEnterScene(long playerId, String sceneId);
}
