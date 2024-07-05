package com.whk.net.rpc.api;

import com.whk.net.rpc.annotation.NoReturnAndNonBlocking;
import com.whk.net.rpc.annotation.OnErrorContinue;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;

import java.io.IOException;

public interface IRpcPlayerBase extends IRpcService {

    /**
     * 获取玩家角色列表
     *
     * @param playerIds 角色id
     * @return
     */
    ListWrapper<PlayerInfo> getPlayers(ListWrapper<Long> playerIds);

    /**
     * 创建角色
     *
     * @param instanceId 网关id
     * @param pid        角色id
     * @return
     */
    void createPlayer(String instanceId, Long pid) throws IOException;

    boolean playerLogin(String gateInstanceId, long playerId);

    @OnErrorContinue
    @NoReturnAndNonBlocking
    void test(String userName);

    String testString(String context);
}
