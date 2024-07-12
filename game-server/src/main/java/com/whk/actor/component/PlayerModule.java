package com.whk.actor.component;

import com.whk.module.ActorModule;
import io.protostuff.Tag;
import lombok.Data;

import java.util.HashMap;

@Data
public class PlayerModule {

    @Tag(1)
    HashMap<String, ActorModule> modules = new HashMap<>();


    public void onLogin(){
        modules.values().forEach(ActorModule::onLogin);
    }
}
