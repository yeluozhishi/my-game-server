package com.whk.scene.register;

import com.whk.scene.map.SceneMessageProcessor;
import com.whk.threadpool.processor.*;

public class SceneMessageProcessorRegister {
    public SceneMessageProcessorRegister() {
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.MAP_PROCESSOR, new SceneMessageProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.PLAYER_PROCESSOR, new PlayerProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.DB_PROCESSOR, new DBProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.RPC_PROCESSOR, new RPCProcessor());
    }
}
