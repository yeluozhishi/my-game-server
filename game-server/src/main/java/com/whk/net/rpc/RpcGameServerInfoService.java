package com.whk.net.rpc;

import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.game.IRpcGameServerInfoService;
import com.whk.server.GameServerManager;
import org.springframework.stereotype.Component;

@Component
@RpcTag
public class RpcGameServerInfoService implements IRpcGameServerInfoService {
    @Override
    public void updateServerInfo(int gateServerId) {
        GameServerManager.getInstance().gateNoticeUpdateServer(gateServerId);
    }
}
