package com.whk.register;

import com.whk.threadpool.processor.PlayerMessageProcessor;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.threadpool.processor.ProcessorManager;
import com.whk.threadpool.processor.RPCMessageProcessor;

public class GateMessageProcessorRegister {

    public GateMessageProcessorRegister() {
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.PLAYER_PROCESSOR, new PlayerMessageProcessor());
        ProcessorManager.INSTANCE.addProcessor(ProcessorId.RPC_PROCESSOR, new RPCMessageProcessor());
    }
}
