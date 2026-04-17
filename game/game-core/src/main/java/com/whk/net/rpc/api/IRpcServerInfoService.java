package com.whk.net.rpc.api;

import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.threadpool.processor.ProcessorId;

public interface IRpcServerInfoService extends IRpcService {

    /**
     * 更新服务器信息
     */
    @MethodDescription()
    void updateServerInfo(int gateServerId);
}  
