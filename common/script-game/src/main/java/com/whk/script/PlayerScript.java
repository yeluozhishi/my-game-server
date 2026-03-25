package com.whk.script;

import com.whk.actor.PlayerMgr;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.server.GameServerManager;
import script.annotation.Script;

import java.util.Objects;

@Script
public class PlayerScript implements IPlayerScript {
    @Override
    public void pushDataToScene(long playerId, String sceneId, Integer serverId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) return;
        var actor = PlayerMgr.INSTANCE.buildPlayerActor(player);
        RpcGameProxyHolder.getInstance().proxy(IRpcScenePlayerActor.class, serverId).pushDataAndEnterScene(actor, sceneId);
    }

    @Override
    public void noticeEnterSceneState(int serverId, long playerId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        if (Objects.nonNull(player)) player.getServerInfo().setPresentServerId(serverId);
    }
}
