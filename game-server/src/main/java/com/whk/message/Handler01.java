package com.whk.message;

import com.whk.actor.PlayerMgr;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.script.ILevelScript;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;

import java.util.Objects;


@GameMessageHandler
@Slf4j
public class Handler01 {

    @HandlerDescription(number = 100, desc = "升级")
    public void message00(Object message, long playerId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        if (Objects.nonNull(player)) ScriptHolder.INSTANCE.getScript(ILevelScript.class).levelUp(player);
    }

    @HandlerDescription(number = 102)
    public void message02(Object message, long playerId) {
    }

    @HandlerDescription(number = 103)
    public void message03(Object message, long playerId) {
        log.info("Hello World 3!");
    }


}
