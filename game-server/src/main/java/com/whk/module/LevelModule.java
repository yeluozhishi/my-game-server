package com.whk.module;

import com.whk.ConfigCacheManager;
import com.whk.comfig.CharacterLevelConfig;
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
        var configDef = ConfigCacheManager.INSTANCE.getConfigCache(CharacterLevelConfig.class).getDef(level);
        if (Objects.nonNull(configDef)){
            return configDef.getAttribute();
        }
        return null;
    }
}
