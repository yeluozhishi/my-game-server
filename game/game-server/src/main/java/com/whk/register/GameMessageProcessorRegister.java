package com.whk.register;

import com.whk.threadpool.processor.*;

public class GameMessageProcessorRegister {
    public GameMessageProcessorRegister() {
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.PLAYER_PROCESSOR, new PlayerProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.DB_PROCESSOR, new DBProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.RPC_PROCESSOR, new RPCProcessor());
    }
}
