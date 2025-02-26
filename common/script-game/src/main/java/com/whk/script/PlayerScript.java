package com.whk.script;

import com.whk.actor.Player;
import com.whk.actor.PlayerMgr;
import com.whk.message.Server;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.server.GameServerManager;
import script.annotation.Script;

@Script
public class PlayerScript implements IPlayerScript{
    @Override
    public void enterScene(Player player, String sceneId) {
        var actor = PlayerMgr.INSTANCE.buildPlayerActor(player);
        var s = GameServerManager.getInstance().getServers().values().stream().filter(server -> server.getServerType() == 3).findAny();
        // todo 需要将server分组，然后依照规则取场景服务器
        RpcGameProxyHolder.getInstance(IRpcScenePlayerActor.class, s.get().getId()).enterScene(actor, sceneId);
    }

    @Override
    public void noticeEnterSceneState(int serverId, long playerId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        player.ifPresent(p -> p.getServerInfo().setPresentServerId(serverId));
    }
}
