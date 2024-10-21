package com.whk.towerAOI.entity;

import com.whk.towerAOI.script.ITowerScript;
import lombok.Getter;
import lombok.Setter;
import script.ScriptHolder;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Tower {

    // 所有对象
    private Map<Long, IMapObject> objectMap = new HashMap<>();
    // 订阅对象
    private Map<Long, IMapObject> watchers = new HashMap<>();

    private int x;

    private int y;

    public void clean(){
        objectMap.clear();
        watchers.clear();
    }

}
