package com.whk.module;


import com.whk.StringUtils;
import io.protostuff.Exclude;

import java.util.HashMap;

public abstract class ActorModule {

    @Exclude
    private HashMap<String, Long> attr = new HashMap<>();

    public abstract HashMap<String, Long> newAttribute();

    public HashMap<String, Long> difference(){
        var newAttr = newAttribute();
        if (StringUtils.isEmpty(newAttr)){
            return newAttr;
        }
        HashMap<String, Long> difference = new HashMap<>();
        newAttr.forEach((key, value) -> {
            long dif = value - attr.getOrDefault(key, 0L);
            if (dif != 0){
                difference.put(key, dif);
            }
        });
        attr = newAttr;
        return difference;
    }
}
