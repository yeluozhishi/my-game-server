package com.whk.rpc.api;

import com.whk.net.enity.MapBean;

import java.util.List;

public interface IRpcPlayerBase {

    /**
     * 获取玩家角色列表
     * @param userId 用户id
     * @return
     */
    public List<MapBean> getPlayers(String userId);
}
