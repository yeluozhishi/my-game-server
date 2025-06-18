package com.whk.scene.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.SceneProto;
import com.whk.protobuf.message.SkillProto;
import com.whk.scene.actor.PlayerActorMgr;
import com.whk.scene.script.ISkillScript;
import com.whk.threadpool.processor.ProcessorId;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;

import java.util.Objects;

@GameMessageHandler
@Slf4j
public class Handler01 {

    // 玩家技能释放，进入场景驱动器
    @HandlerDescription(number = 102, desc = "技能释放", processorId = ProcessorId.MAP_PROCESSOR)
    public void message02(SkillProto.ReqReleaseSkill message, long playerId) {
        var player = PlayerActorMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) {
            return;
        }
        ScriptHolder.INSTANCE.getScript(ISkillScript.class).releaseSkill(player, message.getSkillId(), message.getTargetId());
    }

    @HandlerDescription(number = 103, desc = "测试", processorId = ProcessorId.PLAYER_PROCESSOR)
    public void message03(SceneProto.SceneMessage message, long playerId) {
        log.info(message + "; Hello World scene!");
    }

}