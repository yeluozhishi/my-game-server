package com.whk.script;

import script.scriptInterface.IScript;

public interface IPlayerScript extends IScript {
    void pushDataToScene(long playerId, int mapId, int line, long serverId);

    void noticeEnterSceneState(long serverId, long playerId);

    void getPlayers(int gateServerId, long userId);
}
