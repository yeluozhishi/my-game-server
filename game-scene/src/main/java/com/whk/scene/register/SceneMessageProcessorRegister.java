package com.whk.scene.register;

import com.whk.scene.map.SceneMessageProcessor;
import com.whk.threadpool.processor.DBMessageProcessor;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.threadpool.processor.ProcessorManager;
import com.whk.threadpool.processor.RPCMessageProcessor;

public class SceneMessageProcessorRegister {
    public SceneMessageProcessorRegister() {
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.MAP_PROCESSOR, new SceneMessageProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.DB_PROCESSOR, new DBMessageProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.RPC_PROCESSOR, new RPCMessageProcessor());
    }
}
