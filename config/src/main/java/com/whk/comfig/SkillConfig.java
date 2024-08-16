package com.whk.comfig;

import com.whk.loadconfig.FileCSVConfig;
import com.whk.loadconfig.annotation.ConfigInit;
import com.whk.loadconfig.entity.SkillDef;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

@ConfigInit(fileName = "Skill_skill")
public class SkillConfig extends FileCSVConfig<SkillDef> {

    private final HashMap<Integer, SkillDef> map = new HashMap<>();

    @Override
    protected void afterLoad(LinkedList<SkillDef> linkedList) {
        Map<Integer, SkillDef> skillDefMap = linkedList.stream().collect(Collectors.toMap(SkillDef::getId, skillDef -> skillDef));
        map.putAll(skillDefMap);
    }

    public SkillDef getDef(int id) {
        return map.get(id);
    }
}
