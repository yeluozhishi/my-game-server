package com.whk.script;

import com.whk.actor.component.PlayerModule;
import com.whk.actor.component.attribute.Attributes;
import script.scriptInterface.IScript;

import java.util.HashMap;
import java.util.Set;

public interface IAttributesScript extends IScript {

    public void fromModuleBuildAttribute(PlayerModule playerModule, Attributes attributes);

    void addToFinalAttribute(Attributes attributes, Set<String> changeField);

    void addToAllAttribute(Attributes attributes, HashMap<String, Long> difference);
}
