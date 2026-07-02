package com.whk.script;

import com.whk.StringUtil;
import com.whk.actor.Player;
import com.whk.actor.attribute.Attributes;
import com.whk.actor.component.PlayerModule;
import com.whk.module.ActorModule;
import lombok.extern.slf4j.Slf4j;
import script.annotation.Script;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Script
@Slf4j
public class AttributesScript implements IAttributesScript {

    public void rebuildAttribute(PlayerModule playerModule, Player player) {
        player.getTemporary().getAttributes().getAllAttribute().clear();
        playerModule.getModules().values().forEach(module -> {
            Map<String, Long> newAttribute = module.newAttribute();
            module.setAttr(newAttribute);
            module.getAttr().forEach((key, value) -> player.getTemporary().getAttributes().getAllAttribute().merge(key, value, Long::sum));
        });
        try {
            for (Map.Entry<String, Long> stringLongEntry : player.getTemporary().getAttributes().getAllAttribute().entrySet()) {
                player.getTemporary().getAttributes().getFinalAttribute().setValue(stringLongEntry.getKey(), stringLongEntry.getValue());
            }
        } catch (Throwable e) {
            log.error("属性设置值出错： ", e);
        }
    }

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
        } catch (Throwable e) {
            log.error("属性设置值出错： ", e);
        }
    }

    @Override
    public void addToFinalAttribute(Attributes attributes, Set<String> changeField) {
        var finalAttr = attributes.getFinalAttribute();
        try {
            for (String fieldName : changeField) {
                finalAttr.setValue(fieldName, attributes.getAllAttribute().getOrDefault(fieldName, 0L));
            }
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void addToAllAttribute(Attributes attributes, ActorModule actorModule) {
        Map<String, Long> newAttribute = actorModule.newAttribute();
        if (Objects.isNull(newAttribute) || newAttribute.isEmpty()) return;
        var all = attributes.getAllAttribute();
        var difference = difference(newAttribute, actorModule);
        difference.forEach((key, value) -> {
            var newValue = value + all.getOrDefault(key, 0L);
            if (newValue > 0L) {
                all.put(key, newValue);
            } else {
                all.remove(key);
            }
        });

        addToFinalAttribute(attributes, difference.keySet());
        actorModule.setAttr(newAttribute);
    }

    public Map<String, Long> difference(Map<String, Long> newAttribute, ActorModule actorModule){
        HashMap<String, Long> difference = new HashMap<>();
        actorModule.getAttr().forEach((key, value) -> {
            long dif = newAttribute.getOrDefault(key, 0L) - value;
            difference.merge(key, dif, Long::sum);
        });
        return difference;
    }
}
