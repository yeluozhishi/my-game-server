package com.whk.net.rpc.api.gate;

import com.whk.message.Server;
import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.net.rpc.api.IRpcService;
import com.whk.threadpool.processor.ProcessorId;

import java.util.Map;


public interface IRpcGateServerInfoService extends IRpcService {

    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR, NoReturnAndNonBlocking = false)
    Map<Integer, Server> getServers();

    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR)
    void updateServer();

    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR)
    void noticeEnterSceneState(int id, long id1);
}
