package com.whk.scene.net.rpc;

import com.whk.actor.PlayerActor;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.scene.actor.PlayerActorMgr;
import org.springframework.stereotype.Component;

@Component
@RpcTag
public class RpcScenePlayerActor implements IRpcScenePlayerActor {
    @Override
    public void enterScene(PlayerActor actor) {
        PlayerActorMgr.INSTANCE.addPlayerActor(actor);
    }
}
