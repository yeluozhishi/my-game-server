package com.whk.script;

import com.whk.actor.Player;
import script.scriptInterface.IScript;

public interface ILevelScript extends IScript {
    void levelUp(Player player);
}
