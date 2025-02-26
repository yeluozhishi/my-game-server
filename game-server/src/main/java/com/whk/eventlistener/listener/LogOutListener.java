package com.whk.eventlistener.listener;

import com.whk.eventlistener.event.LogoutEvent;
import com.whk.listener.eventlistener.listener.AbstractEventListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogOutListener extends AbstractEventListener<LogoutEvent> {

    @Override
    public void dealEvent(LogoutEvent event) {
        log.info("登出事件：" + event.getUserId());
    }

}
