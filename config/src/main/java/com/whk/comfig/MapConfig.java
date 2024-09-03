package com.whk.comfig;

import com.whk.entity.MapDef;
import com.whk.loadconfig.FileCSVConfig;
import com.whk.loadconfig.annotation.ConfigInit;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ConfigInit(fileName = "Map_map")
public class MapConfig extends FileCSVConfig<MapDef> {

    private Map<Integer, MapDef> hashMap = new HashMap<>();

    @Override
    protected void afterLoad(LinkedList<MapDef> linkedList) {
        hashMap = linkedList.stream().collect(Collectors.toMap(MapDef::getId, Function.identity()));
    }

    public MapDef getDef(int id){
        return hashMap.get(id);
    }

}
