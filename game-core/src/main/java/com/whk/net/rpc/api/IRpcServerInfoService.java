package com.whk.net.rpc.api;

import com.whk.message.Server;

import java.util.Map;


public interface IRpcServerInfoService extends IRpcService {
    Map<Integer, Server> getServers();
}  
