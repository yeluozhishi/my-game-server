package com.whk.listener;

import com.whk.SpringUtils;
import com.whk.serverinfo.ServerManager;

public enum ListenerRegister {
    INSTANCE;

    public void registerHeartbeatListener(ServerManager manager) {
        SpringUtils.getBean(HeartbeatListener.class).register(manager);
    }
}
