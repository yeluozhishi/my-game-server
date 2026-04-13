package com.whk.actor.component;

import com.whk.AbstractCacheableData;
import com.whk.SpringUtils;
import com.whk.db.entity.PlayerModuleEntity;
import com.whk.module.ActorModule;
import com.whk.net.kafka.MessageInnerCoder;
import com.whk.service.player.PlayerModuleService;
import io.protostuff.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class PlayerModule extends AbstractCacheableData<PlayerModuleEntity, Long> {

    @Tag(1)
    private HashMap<String, ActorModule> modules = new HashMap<>();


    public <T extends ActorModule> T getModule(Class<T> tClass) {
        ActorModule module = modules.get(tClass.getName());
        return tClass.cast(module);
    }


    @Override
    public void update() {
        getEntity().setData(MessageInnerCoder.INSTANCE.getProtostuffSerializeUtil().encode(this).array());
        SpringUtils.getBean(PlayerModuleService.class).update(getId(), this);
    }

    @Override
    public Long getId() {
        return getEntity().getId();
    }
}
