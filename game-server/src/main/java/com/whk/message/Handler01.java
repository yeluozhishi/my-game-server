package com.whk.message;

import com.whk.actor.PlayerMgr;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.MessageProto;
import com.whk.script.ILevelScript;
import com.whk.script.IPlayerScript;
import script.ScriptHolder;


@GameMessageHandler
public class Handler01 {

    @HandlerDescription(number = 100, desc = "升级")
    public void message00(MessageProto.Message message, long playerId) {
        PlayerMgr.INSTANCE.getPlayer(playerId)
                .ifPresent(player -> ScriptHolder.INSTANCE.getScript(ILevelScript.class).levelUp(player));
    }

    @HandlerDescription(number = 102, desc = "")
    public void message02(MessageProto.Message message, long playerId) {
    }

    @HandlerDescription(number = 103, desc = "")
    public void message03(MessageProto.Message message, long playerId) {
        System.out.println("Hello World 3!");
    }

    @HandlerDescription(number = 104, desc = "进入场景")
    public void message04(MessageProto.Message message, long playerId) {
        PlayerMgr.INSTANCE.getPlayer(playerId)
                .ifPresent(player -> ScriptHolder.INSTANCE.getScript(IPlayerScript.class).enterScene(player));
    }

}
