package com.whk.rpc.api;

import com.whk.serverinfo.Server;

import java.util.List;

public interface IRpcServer {

    /**
     * 获取server列表
     * @param zone
     * @return
     */
    public List<Server> getServer(int zone);

}
