package com.whk.net.rpc;

import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.IRpcServerInfoService;
import com.whk.server.GameServerManager;
import org.springframework.stereotype.Component;

@Component
@RpcTag
public class RpcServerInfoService implements IRpcServerInfoService {
    @Override
    public void updateServerInfo(long gateServerId) {
        GameServerManager.getInstance().gateNoticeUpdateServer(gateServerId);
    }
}
