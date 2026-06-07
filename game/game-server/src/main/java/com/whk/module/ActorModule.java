package com.whk.module;


import io.protostuff.Exclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class ActorModule {

    @Exclude
    private Map<String, Long> attr = new HashMap<>();

    public abstract Map<String, Long> newAttribute();

}
