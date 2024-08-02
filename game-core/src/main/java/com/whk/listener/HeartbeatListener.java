package com.whk.listener;

import com.whk.serverinfo.ServerManager;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * spring cloud heartbeat监听
 */
@Component
public class HeartbeatListener implements ApplicationListener<HeartbeatEvent> {

    private ServerManager manager;

    @Override
    public void onApplicationEvent(HeartbeatEvent event) {
        manager.doAction();
    }

    public void register(ServerManager manager) {
        this.manager = manager;
    }
}