package com.whk.message;

import com.whk.actor.PlayerMgr;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.annotation.ThreadAssign;
import com.whk.script.ILevelScript;
import com.whk.threadpool.TheadType;
import com.whk.protobuf.message.MessageProto;
import script.ScriptHolder;


@GameMessageHandler
public class Handler01 {

    @ThreadAssign
    @HandlerDescription(number = "100", desc = "使用道具")
    public void message00(MessageProto.Message message, long playerId){
        PlayerMgr.INSTANCE.getPlayer(playerId)
                .ifPresent(player -> ScriptHolder.INSTANCE.getScript(ILevelScript.class).levelUp(player));
    }
    @ThreadAssign(TheadType.SCENE_THREAD)
    public void message02(MessageProto.Message message, long playerId){
        System.out.println("Hello World 2!");
    }

    public void message03(MessageProto.Message message, long playerId){
        System.out.println("Hello World 3!");
    }



}
