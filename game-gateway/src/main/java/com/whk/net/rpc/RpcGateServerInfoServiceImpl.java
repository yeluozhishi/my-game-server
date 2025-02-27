package com.whk.net.rpc;

import com.whk.message.Server;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.gate.IRpcGateServerInfoService;
import com.whk.script.IUserScript;
import com.whk.server.GateServerManager;
import org.springframework.stereotype.Component;
import script.ScriptHolder;

import java.util.Map;

@Component
@RpcTag
public class RpcGateServerInfoServiceImpl implements IRpcGateServerInfoService {

    @Override
    public Map<Integer, Server> getServers() {
        return GateServerManager.getInstance().getServers();
    }

    @Override
    public void updateServer() {
        GateServerManager.getInstance().updateOnlineServers(false);
    }

    @Override
    public void noticeEnterSceneState(int serverId, long playerId) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).noticeEnterSceneState(serverId, playerId);
    }
}
