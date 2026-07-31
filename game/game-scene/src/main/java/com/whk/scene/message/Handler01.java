package com.whk.scene.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.SceneProto;
import com.whk.protobuf.message.SkillProto;
import com.whk.scene.actor.PlayerActorMgr;
import com.whk.scene.script.ISkillScript;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.towerAOI.entity.Point;
import com.whk.towerAOI.script.ITowerScript;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;

import java.util.Objects;

@GameMessageHandler
@Slf4j
public class Handler01 {

    // 玩家技能释放，进入场景驱动器
    @HandlerDescription(desc = "技能释放", processorId = ProcessorId.MAP_PROCESSOR)
    public void message102(SkillProto.ReqReleaseSkill message, long playerId) {
        var player = PlayerActorMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) {
            return;
        }
        ScriptHolder.INSTANCE.getScript(ISkillScript.class).releaseSkill(player, message.getSkillId(), message.getTargetId());
    }

    @HandlerDescription(desc = "测试", processorId = ProcessorId.PLAYER_PROCESSOR)
    public void message103(SceneProto.SceneMessage message, long playerId) {
        log.info(message + "; Hello World scene!");
    }

    @HandlerDescription(desc = "移动", processorId = ProcessorId.PLAYER_PROCESSOR)
    public void message104(SceneProto.ReqPlayerMove message, long playerId) {
        var player = PlayerActorMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) {
            return;
        }
        ScriptHolder.INSTANCE.getScript(ITowerScript.class).moveToNextPoint(player, new Point(message.getX(), message.getY(), message.getZ(), message.getDir()));
    }
}