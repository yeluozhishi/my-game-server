package com.whk.config;

import com.whk.entity.MapDef;
import com.whk.loadconfig.AbstractConfig;
import com.whk.loadconfig.annotation.ConfigInit;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ConfigInit(fileName = "Map_map")
@Getter
public class MapConfig extends AbstractConfig<MapDef> {
    @Getter
    private static MapConfig instance = new MapConfig();

    private Map<Integer, MapDef> hashMap = new HashMap<>();

    @Override
    public void afterLoad(List<MapDef> list) {
        hashMap = list.stream().collect(Collectors.toMap(MapDef::getId, Function.identity()));
    }

    @Override
    public void setInstance() {
        instance = this;
    }

    public MapDef getDef(int id){
        return hashMap.get(id);
    }

}
