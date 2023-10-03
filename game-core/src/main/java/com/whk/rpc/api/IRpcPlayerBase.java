package com.whk.rpc.api;

import com.whk.rpc.annotation.NoReturnAndNonBlocking;
import com.whk.rpc.annotation.OnErrorContinue;
import com.whk.rpc.model.PlayerInfo;

import java.io.IOException;
import java.util.List;

public interface IRpcPlayerBase {

    /**
     * 获取玩家角色列表
     *
     * @param userName 用户id
     * @return
     */
    public List<PlayerInfo> getPlayers(Long userName);

    /**
     * 创建角色
     *
     * @param userId
     * @param userName   用户id
     * @param instanceId 网关id
     * @param pid        角色id
     * @return
     */
    public Boolean createPlayer(Long userId, String userName, String instanceId, Long pid) throws IOException;

    @OnErrorContinue
    @NoReturnAndNonBlocking
    public void test(String userName);
}
