package com.whk.config;

import com.whk.entity.CharacterLevelDef;
import com.whk.loadconfig.AbstractConfig;
import com.whk.loadconfig.annotation.ConfigInit;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ConfigInit(fileName = "Character_attribute_level")
@Setter
@Getter
public class CharacterLevelConfig extends AbstractConfig<CharacterLevelDef> {

    @Getter
    private static CharacterLevelConfig instance;


    private Map<Integer, CharacterLevelDef> hashMap = new HashMap<>();


    @Override
    public void setInstance() {
        instance = this;
    }

    public CharacterLevelDef getDef(int id) {
        return hashMap.get(id);
    }

    @Override
    public void afterLoad(List<CharacterLevelDef> list) {
        hashMap = list.stream().collect(Collectors.toMap(CharacterLevelDef::getId, Function.identity()));
    }
}
