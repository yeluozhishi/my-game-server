package com.whk.net.rpc.api;

import com.whk.net.rpc.annotation.NoReturnAndNonBlocking;
import com.whk.net.rpc.annotation.OnErrorContinue;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
     * @param gateTopic 网关
     * @param pid        角色id
     * @return
     */
    @NoReturnAndNonBlocking
    void createPlayer(String gateTopic, Long pid) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    @NoReturnAndNonBlocking
    void playerLogin(String gateTopic, long playerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    @OnErrorContinue
    @NoReturnAndNonBlocking
    void test(String userName);

    String testString(String context);
}
