package com.whk.net.rpc.proxy;

import com.whk.message.Server;
import com.whk.net.rpc.api.IRpcService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class RpcServerProxy {

    public <T extends IRpcService> T proxy(Class<T> clazz, int serverId) {
        var server = getServer(serverId);
        if (Objects.nonNull(server)) {
            return RpcProxy.create(clazz, rpcRequestTopic(server.getId()), String.valueOf(serverId));
        }
        log.error("服务器不存在{}", serverId);
        return null;
    }


    public abstract Server getServer(int serverId);


    public abstract String rpcRequestTopic(int serverId);
}
