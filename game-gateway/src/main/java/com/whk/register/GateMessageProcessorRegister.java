package com.whk.register;

import com.whk.threadpool.processor.PlayerProcessor;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.threadpool.processor.ProcessorManager;
import com.whk.threadpool.processor.RPCProcessor;

public class GateMessageProcessorRegister {

    public GateMessageProcessorRegister() {
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.PLAYER_PROCESSOR, new PlayerProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.RPC_PROCESSOR, new RPCProcessor());
    }
}
