package com.whk.actor.component;

import com.whk.gamedb.entity.PlayerModuleEntity;
import com.whk.module.ActorModule;
import io.protostuff.Tag;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class PlayerModule extends AbstractComponent<PlayerModuleEntity> {

    @Tag(1)
    HashMap<String, ActorModule> modules = new HashMap<>();


    public void onLogin() {
        modules.values().forEach(ActorModule::onLogin);
    }

}
