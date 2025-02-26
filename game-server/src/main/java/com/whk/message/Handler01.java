package com.whk.message;

import com.whk.actor.PlayerMgr;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.SceneProto;
import com.whk.script.ILevelScript;
import com.whk.script.IPlayerScript;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;


@GameMessageHandler
@Slf4j
public class Handler01 {

    @HandlerDescription(number = 100, desc = "升级")
    public void message00(Object message, long playerId) {
        PlayerMgr.INSTANCE.getPlayer(playerId)
                .ifPresent(player -> ScriptHolder.INSTANCE.getScript(ILevelScript.class).levelUp(player));
    }

    @HandlerDescription(number = 102)
    public void message02(Object message, long playerId) {
    }

    @HandlerDescription(number = 103)
    public void message03(Object message, long playerId) {
        log.info("Hello World 3!");
    }

    @HandlerDescription(number = 104, desc = "进入场景")
    public void message04(SceneProto.ReqEnterScene message, long playerId) {
        PlayerMgr.INSTANCE.getPlayer(playerId)
                .ifPresent(player -> ScriptHolder.INSTANCE.getScript(IPlayerScript.class).enterScene(player, message.getSceneId()));
    }

}
