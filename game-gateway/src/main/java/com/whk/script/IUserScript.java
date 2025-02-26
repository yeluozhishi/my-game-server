package com.whk.script;

import script.scriptInterface.IScript;

public interface IUserScript extends IScript {
    void noticeEnterSceneState(int serverId, long playerId);
}
