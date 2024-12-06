package com.whk.net.rpc.api.gate;

import com.whk.message.Server;
import com.whk.net.rpc.api.IRpcService;

import java.util.Map;


public interface IRpcGateServerInfoService extends IRpcService {
    Map<Integer, Server> getServers();
}  
