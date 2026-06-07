package com.whk.script;

import com.whk.actor.component.PlayerModule;
import com.whk.actor.attribute.Attributes;
import com.whk.module.ActorModule;
import script.scriptInterface.IScript;

import java.util.Set;

public interface IAttributesScript extends IScript {

    void fromModuleBuildAttribute(PlayerModule playerModule, Attributes attributes);

    void addToFinalAttribute(Attributes attributes, Set<String> changeField);

    void addToAllAttribute(Attributes attributes, ActorModule difference);
}
