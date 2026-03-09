package com.whk.actor.build;

import com.whk.actor.component.BasicInfo;
import com.whk.module.ActorModule;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Getter
@Setter
@Accessors(chain = true)
@Slf4j
public class PlayerBuilder {
    BasicInfo basicInfo;

    String gateTopic;

    int gateServerId;

    HashMap<String, Class<? extends ActorModule>> registerModules;

}
