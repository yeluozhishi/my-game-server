package com.whk.net.rpc.api.scene;

import com.whk.actor.PlayerActor;
import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.net.rpc.api.IRpcService;
import com.whk.threadpool.processor.ProcessorId;

public interface IRpcScenePlayerActor extends IRpcService {
    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR)
    void enterScene(PlayerActor actor, String sceneId);
}
