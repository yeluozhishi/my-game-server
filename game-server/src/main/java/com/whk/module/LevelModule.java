package com.whk.module;

import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelModule extends ActorModule{
    @Tag(1)
    private int level;
    @Tag(2)
    private long exp;
}
