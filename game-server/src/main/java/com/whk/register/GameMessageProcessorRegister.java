package com.whk.register;

import com.whk.threadpool.processor.*;

public class GameMessageProcessorRegister {
    public GameMessageProcessorRegister() {
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.PLAYER_PROCESSOR, new PlayerMessageProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.DB_PROCESSOR, new DBMessageProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.RPC_PROCESSOR, new RPCMessageProcessor());
    }
}
