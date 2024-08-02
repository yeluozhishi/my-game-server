package com.whk.listener;

import com.whk.SpringUtils;
import com.whk.listener.HeartbeatListener;
import com.whk.serverinfo.ServerManager;

/**
 * todo 需要改掉
 */
public enum ListenerRegister {
    INSTANCE;

    public void registerHeartbeatListener(ServerManager manager) {
        SpringUtils.getBean(HeartbeatListener.class).register(manager);
    }
}
