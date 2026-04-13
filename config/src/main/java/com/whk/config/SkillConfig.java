package com.whk.config;

import com.whk.entity.SkillDef;
import com.whk.loadconfig.AbstractConfig;
import com.whk.loadconfig.annotation.ConfigInit;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ConfigInit(fileName = "Skill_skill")
@Getter
public class SkillConfig extends AbstractConfig<SkillDef> {

    @Getter
    private static SkillConfig instance = new SkillConfig();

    private Map<Integer, SkillDef> map = new HashMap<>();

    @Override
    public void afterLoad(List<SkillDef> list) {
        map = list.stream().collect(Collectors.toMap(SkillDef::getId, skillDef -> skillDef));
    }

    @Override
    public void setInstance() {
        instance = this;
    }

    public SkillDef getDef(int id) {
        return map.get(id);
    }
}
