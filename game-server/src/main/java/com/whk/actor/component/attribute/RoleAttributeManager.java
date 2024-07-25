package com.whk.actor.component.attribute;

import com.whk.module.ActorModule;
import com.whk.script.IAttributesScript;
import script.ScriptHolder;

import java.util.Objects;

public enum RoleAttributeManager {
    INSTANCE;

    public void calculateModuleAndRebuild(Attributes attributes, ActorModule actorModule){
        if (Objects.isNull(actorModule.difference())) return;
        ScriptHolder.INSTANCE.getScript(IAttributesScript.class)
                .addToAllAttribute(attributes, actorModule.difference());

    }


}
