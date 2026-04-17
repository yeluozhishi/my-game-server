package com.whk.net.rpc;

import com.whk.message.MapBean;
import com.whk.message.Server;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.gate.IRpcGateServerInfoService;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.script.IUserScript;
import com.whk.server.GateServerManager;
import org.springframework.stereotype.Component;
import script.ScriptHolder;

import java.util.List;
import java.util.Map;

@Component
@RpcTag
public class RpcGateServerInfoServiceImpl implements IRpcGateServerInfoService {

    @Override
    public Map<Long, Server> getServers() {
        return GateServerManager.getInstance().getOnlineServers();
    }

    @Override
    public void updateServer() {
        GateServerManager.getInstance().updateOnlineServers();
    }

    @Override
    public void noticeEnterSceneState(long serverId, long playerId) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).noticeEnterSceneState(serverId, playerId);
    }

    @Override
    public void resCreatePlayer(MapBean mapBean, long userId) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).resCreatePlayer(mapBean, userId);
    }

    @Override
    public void resCreatePlayerFailure(MapBean messageMapBean, long userId) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).resCreatePlayerFailure(messageMapBean, userId);
    }

    @Override
    public void resPlayerLogin(long userId, MapBean messageMapBean) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).resPlayerLogin(userId, messageMapBean);
    }

    @Override
    public void resGetPlayers(List<PlayerInfo> result, long userId) {
        ScriptHolder.INSTANCE.getScript(IUserScript.class).resGetPlayers(result, userId);
    }
}
