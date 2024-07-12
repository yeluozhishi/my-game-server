package com.whk.loadconfig.Ipml;

import com.whk.loadconfig.FileCSVConfig;
import com.whk.loadconfig.annotation.ConfigInit;
import com.whk.loadconfig.entity.CharacterLevelDef;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

@ConfigInit(fileName = "Character_attribute_level")
public class CharacterLevelConfig extends FileCSVConfig<CharacterLevelDef> {
    public HashMap<Integer, CharacterLevelDef> hashMap = new HashMap<>();


    @Override
    protected void afterLoad(LinkedList<CharacterLevelDef> linkedList) {
        hashMap = (HashMap<Integer, CharacterLevelDef>) linkedList.stream().collect(Collectors.toMap(CharacterLevelDef::getId, f -> f));
    }


}
