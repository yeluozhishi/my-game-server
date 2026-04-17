package com.whk.scene.net.rpc;

import com.whk.actor.PlayerActor;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.scene.map.script.ISceneScript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import script.ScriptHolder;

@Component
@RpcTag
@Slf4j
public class RpcScenePlayerActor implements IRpcScenePlayerActor {
    @Override
    public void pushDataAndEnterScene(PlayerActor actor, int mapId, int line) {
        ScriptHolder.INSTANCE.getScript(ISceneScript.class).pushDataAndEnterScene(actor, mapId, line);
    }

    @Override
    public void enterScene(long playerId, int mapId, int line) {
        ScriptHolder.INSTANCE.getScript(ISceneScript.class).playerEnterScene(playerId, mapId, line);
    }
}
