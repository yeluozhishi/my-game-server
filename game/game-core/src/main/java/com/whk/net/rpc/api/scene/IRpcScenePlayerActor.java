package com.whk.net.rpc.api.scene;

import com.whk.actor.PlayerActor;
import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.net.rpc.api.IRpcService;
import com.whk.threadpool.processor.ProcessorId;

public interface IRpcScenePlayerActor extends IRpcService {
    @MethodDescription()
    void pushDataAndEnterScene(PlayerActor actor, int mapId, int line);

    @MethodDescription()
    void enterScene(long playerId, int sceneId, int line);
}
