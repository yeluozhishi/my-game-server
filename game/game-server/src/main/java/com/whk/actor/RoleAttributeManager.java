package com.whk.actor;

import com.whk.module.ActorModule;
import com.whk.script.IAttributesScript;
import script.ScriptHolder;

import java.util.Map;
import java.util.Objects;

public enum RoleAttributeManager {
    INSTANCE;

    public void calculateModuleAndRebuild(Player player, ActorModule actorModule){
        ScriptHolder.INSTANCE.getScript(IAttributesScript.class)
                .addToAllAttribute(player.getAttributes(), actorModule);
    }
}
