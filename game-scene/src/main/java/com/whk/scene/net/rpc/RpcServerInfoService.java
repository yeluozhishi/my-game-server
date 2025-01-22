package com.whk.scene.net.rpc;

import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.IRpcServerInfoService;
import com.whk.scene.server.SceneServerManager;
import org.springframework.stereotype.Component;

@Component
@RpcTag
public class RpcServerInfoService implements IRpcServerInfoService {
    @Override
    public void updateServerInfo(int gateServerId) {
        SceneServerManager.getInstance().gateNoticeUpdateServer(gateServerId);
    }
}
