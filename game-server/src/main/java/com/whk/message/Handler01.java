package com.whk.message;

import com.whk.actor.Player;
import com.whk.actor.PlayerMgr;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.MessageProto;
import com.whk.scene.AbstractScene;
import com.whk.scene.SceneManager;
import com.whk.script.ILevelScript;
import com.whk.skill.SkillBuilder;
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
        Player player = PlayerMgr.INSTANCE.getPlayer(playerId).get();
        AbstractScene scene = SceneManager.INSTANCE.getScene(1008L);
        scene.addSkill(SkillBuilder.buildSkill(player, message.getReqReleaseSkill().getSkillId(), player.getId()));
        System.out.println("Hello World 2!");
    }

    public void message03(MessageProto.Message message, long playerId) {
        System.out.println("Hello World 3!");
    }

}
