package com.whk.script.game;

import com.whk.StringUtils;
import com.whk.actor.component.PlayerModule;
import com.whk.actor.component.attribute.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

public class AttributesScript implements IAttributesScript {

    private final Logger logger = Logger.getLogger(AttributesScript.class.getName());

    @Override
    public void fromModuleBuildAttribute(PlayerModule playerModule, Attributes attributes) {
        try {
            playerModule.getModules().values().forEach(module -> {
                var moduleAttr = module.newAttribute();
                if (!StringUtils.isEmpty(moduleAttr)){
                    attributes.getAllAttribute().merge()
                }
            });

            for (Map.Entry<String, Long> stringLongEntry : attributes.getAllAttribute().entrySet()) {
                attributes.getFinalAttribute().setValue(stringLongEntry.getKey(), stringLongEntry.getValue());
            }
        } catch (IllegalAccessException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void addToFinalAttribute(Attributes attributes, Set<String> changeField) {
        var finalAttr = attributes.getFinalAttribute();
        try {
            for (String fieldName : changeField) {
                var field = finalAttr.getClass().getField(fieldName);
                field.setAccessible(true);
                field.set(finalAttr, attributes.getAllAttribute().getOrDefault(fieldName, 0L));
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void addToAllAttribute(Attributes attributes, HashMap<String, Long> difference) {
        var all = attributes.getAllAttribute();
        difference.forEach((key, value) -> {
            var newValue = value + all.getOrDefault(key, 0L);
            if (newValue > 0L) {
                all.put(key, newValue);
            } else {
                all.remove(key);
            }
        });

        addToFinalAttribute(attributes, difference.keySet());
    }
}
