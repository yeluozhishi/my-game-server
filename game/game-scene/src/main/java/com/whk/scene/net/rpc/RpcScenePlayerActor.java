package com.whk.scene.net.rpc;

import com.whk.actor.PlayerActor;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.game.IRpcGamePlayerBase;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.scene.actor.PlayerActorMgr;
import com.whk.scene.map.AbstractScene;
import com.whk.scene.map.SceneManager;
import com.whk.scene.map.script.ISceneScript;
import com.whk.scene.net.RpcSceneProxyHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import script.ScriptHolder;

@Component
@RpcTag
@Slf4j
public class RpcScenePlayerActor implements IRpcScenePlayerActor {
    @Override
    public void pushDataAndEnterScene(PlayerActor actor, String sceneId) {
        ScriptHolder.INSTANCE.getScript(ISceneScript.class).pushDataAndEnterScene(actor, sceneId);
    }

    @Override
    public void enterScene(long playerId, String sceneId) {
        ScriptHolder.INSTANCE.getScript(ISceneScript.class).playerEnterScene(playerId, sceneId);
    }
}
