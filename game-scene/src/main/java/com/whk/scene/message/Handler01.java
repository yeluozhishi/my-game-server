package com.whk.scene.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.MessageProto;
import com.whk.scene.actor.PlayerActorMgr;
import com.whk.scene.script.ISkillScript;
import com.whk.threadpool.processor.ProcessorId;
import script.ScriptHolder;

@GameMessageHandler
public class Handler01 {

    // 玩家技能释放，进入场景驱动器
    @HandlerDescription(number = 102, desc = "技能释放", processorId = ProcessorId.MAP_PROCESSOR)
    public void message02(MessageProto.Message message, long playerId) {
        PlayerActorMgr.INSTANCE.getPlayer(playerId).ifPresent(player -> ScriptHolder.INSTANCE.getScript(ISkillScript.class).releaseSkill(player, message.getReqReleaseSkill().getSkillId()));
    }

    @HandlerDescription(number = 103, desc = "测试", processorId = ProcessorId.MAP_PROCESSOR)
    public void message03(MessageProto.Message message, long playerId) {
        System.out.println("Hello World scene!");
    }

}