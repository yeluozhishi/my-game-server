package com.whk.net.rpc;

import com.whk.message.Server;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.IRpcServerInfoService;
import com.whk.server.GateServerManager;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RpcTag
public class RpcServerInfoServiceImpl implements IRpcServerInfoService {

    @Override
    public Map<Integer, Server> getServers() {
        return GateServerManager.getInstance().getServers();
    }
}
