package com.whk.script;

import com.whk.StringUtil;
import com.whk.actor.attribute.Attributes;
import com.whk.actor.component.PlayerModule;
import lombok.extern.slf4j.Slf4j;
import script.annotation.Script;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Script
@Slf4j
public class AttributesScript implements IAttributesScript {

    @Override
    public void fromModuleBuildAttribute(PlayerModule playerModule, Attributes attributes) {
        try {
            playerModule.getModules().values().forEach(module -> {
                var moduleAttr = module.newAttribute();
                if (!StringUtil.isEmpty(moduleAttr)){
                    moduleAttr.forEach((key, value) -> attributes.getAllAttribute().merge(key, value, Long::sum));
                }
            });

            for (Map.Entry<String, Long> stringLongEntry : attributes.getAllAttribute().entrySet()) {
                attributes.getFinalAttribute().setValue(stringLongEntry.getKey(), stringLongEntry.getValue());
            }
        } catch (Exception e) {
            log.error("属性设置值出错： ", e);
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
        } catch (Exception e) {
            log.error(e.getMessage());
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
