package com.whk.net.rpc.api.game;

import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.net.rpc.api.IRpcService;
import com.whk.protobuf.message.CreatePlayerProto;

import java.lang.reflect.InvocationTargetException;

public interface IRpcGamePlayerBase extends IRpcService {

    /**
     * 获取玩家角色列表
     *
     * @param userId 用户id
     */
    @MethodDescription()
    void getPlayers(int gateServerId, long userId);

    /**
     * 创建角色
     *
     * @param gateTopic 网关
     */

    @MethodDescription()
    void createPlayer(String gateTopic, int gateServerId, CreatePlayerProto.CreatePlayer message) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    @MethodDescription()
    void playerLogin(String gateTopic, long playerId, int gateServerId, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    @MethodDescription()
    void test(String userName);

    @MethodDescription(NoReturnAndNonBlocking = false)
    String testString(String context);

    @MethodDescription()
    void noticeEnterSceneState(long serverId, long playerId);

    @MethodDescription()
    void pushDataToScene(long playerId, int mapId, int line , long serverId);

}
