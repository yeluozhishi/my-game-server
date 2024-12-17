package com.whk.message;

import com.whk.actor.PlayerMgr;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.MessageProto;
import com.whk.script.ILevelScript;
import com.whk.script.ISkillScript;
import script.ScriptHolder;


@GameMessageHandler
public class Handler01 {

    @HandlerDescription(number = "100", desc = "升级")
    public void message00(MessageProto.Message message, long playerId) {
        PlayerMgr.INSTANCE.getPlayer(playerId)
                .ifPresent(player -> ScriptHolder.INSTANCE.getScript(ILevelScript.class).levelUp(player));
    }

    // 玩家技能释放，进入场景驱动器
    @HandlerDescription(number = "102", desc = "技能释放")
    public void message02(MessageProto.Message message, long playerId) {
        PlayerMgr.INSTANCE.getPlayer(playerId).ifPresent( player -> ScriptHolder.INSTANCE.getScript(ISkillScript.class).releaseSkill(player, message.getReqReleaseSkill().getSkillId()));
    }

    public void message03(MessageProto.Message message, long playerId) {
        System.out.println("Hello World 3!");
    }

}
