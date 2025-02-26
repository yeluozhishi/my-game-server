package com.whk.script;

import com.whk.actor.Player;
import script.scriptInterface.IScript;

public interface IPlayerScript extends IScript {
    void enterScene(Player player, String sceneId);

    void noticeEnterSceneState(int serverId, long playerId);
}
