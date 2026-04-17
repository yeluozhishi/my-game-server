package com.whk.net.rpc.api.gate;

import com.whk.message.MapBean;
import com.whk.message.Server;
import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.threadpool.processor.ProcessorId;

import java.util.List;
import java.util.Map;


public interface IRpcGateServerInfoService extends IRpcService {

    @MethodDescription(NoReturnAndNonBlocking = false)
    Map<Long, Server> getServers();

    @MethodDescription()
    void updateServer();

    @MethodDescription()
    void noticeEnterSceneState(long serverId, long playerId);

    @MethodDescription()
    void resCreatePlayer(MapBean mapBean, long userId);

    @MethodDescription()
    void resCreatePlayerFailure(MapBean messageMapBean, long userId);

    @MethodDescription()
    void resPlayerLogin(long userId, MapBean messageMapBean);

    @MethodDescription()
    void resGetPlayers(List<PlayerInfo> result, long userId);
}
