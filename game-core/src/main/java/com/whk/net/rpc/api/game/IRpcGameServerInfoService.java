package com.whk.net.rpc.api.game;

import com.whk.net.rpc.api.IRpcService;


public interface IRpcGameServerInfoService extends IRpcService {

    /**
     * 更新服务器信息
     */
    void updateServerInfo(int gateServerId);
}  
