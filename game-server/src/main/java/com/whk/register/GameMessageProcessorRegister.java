package com.whk.register;

import com.whk.threadpool.processor.PlayerMessageProcessor;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.threadpool.processor.ProcessorManager;
import com.whk.scene.SceneMessageProcessor;

public class GameMessageProcessorRegister {
    public GameMessageProcessorRegister() {
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.PLAYER_PROCESSOR, new PlayerMessageProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.MAP_PROCESSOR, new SceneMessageProcessor());
    }
}
