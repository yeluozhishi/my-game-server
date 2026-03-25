package com.whk.module;

import com.whk.config.CharacterLevelConfig;
import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Objects;

@Getter
@Setter
public class LevelModule extends ActorModule{
    @Tag(1)
    private int level;
    @Tag(2)
    private long exp;

    @Override
    public HashMap<String, Long> newAttribute() {
        var configDef = CharacterLevelConfig.getInstance().getDef(level);
        if (Objects.nonNull(configDef)){
            return configDef.getAttribute();
        }
        return null;
    }
}
