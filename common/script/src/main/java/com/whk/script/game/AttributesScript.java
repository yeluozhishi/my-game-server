package com.whk.script.game;

import com.whk.actor.component.attribute.AttributeTransForm;
import com.whk.actor.component.attribute.Attributes;

import java.util.HashMap;

public class AttributesScript implements IAttributesScript{
    @Override
    public void calculateFinalAttribute(Attributes attributes) {
        attributes.getAllAttribute()
                .forEach((key, num) -> AttributeTransForm.setAttribute(key, attributes.getFinalAttribute(), num.intValue(), num));
    }


    public void addToFinalAttribute(Attributes attributes, HashMap<String, Long> difference){

    }
}
