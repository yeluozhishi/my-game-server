package com.whk.script.game;

import com.whk.actor.component.attribute.Attributes;
import script.scriptInterface.IScript;

public interface IAttributesScript extends IScript {

    public void calculateFinalAttribute(Attributes attributes);
}
