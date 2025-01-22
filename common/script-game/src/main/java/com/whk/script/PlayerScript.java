package com.whk.script;

import com.whk.actor.Player;
import com.whk.actor.PlayerMgr;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import script.annotation.Script;

@Script
public class PlayerScript implements IPlayerScript{
    @Override
    public void enterScene(Player player) {
        if (!player.getServerInfo().inScene()) return;
        var actor = PlayerMgr.INSTANCE.buildPlayerActor(player);
        RpcGameProxyHolder.getInstance(IRpcScenePlayerActor.class, player).enterScene(actor);
    }
}
