package com.whk.script;

import script.scriptInterface.IScript;

public interface IPlayerScript extends IScript {
    void pushDataToScene(long playerId, String sceneId, Integer serverId);

    void noticeEnterSceneState(int serverId, long playerId);
}
