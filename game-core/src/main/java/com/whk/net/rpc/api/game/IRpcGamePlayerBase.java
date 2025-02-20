package com.whk.net.rpc.api.game;

import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;
import com.whk.threadpool.processor.ProcessorId;

import java.lang.reflect.InvocationTargetException;

public interface IRpcGamePlayerBase extends IRpcService {

    /**
     * 获取玩家角色列表
     *
     * @param userId
     * @param playerIds 角色id
     * @return
     */
    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR)
    ListWrapper<PlayerInfo> getPlayers(long userId, ListWrapper<Long> playerIds);

    /**
     * 创建角色
     *
     * @param gateTopic 网关
     * @param pid       角色id
     * @return
     */
    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR, NoReturnAndNonBlocking = true)
    void createPlayer(String gateTopic, Long pid) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR)
    void playerLogin(String gateTopic, long playerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR, NoReturnAndNonBlocking = true, OnErrorContinue = true)
    void test(String userName);

    @MethodDescription(processorId = ProcessorId.RPC_PROCESSOR)
    String testString(String context);
}
