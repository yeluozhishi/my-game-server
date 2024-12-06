package com.whk.eventlistener.listener;

import com.whk.eventlistener.event.LogoutEvent;
import com.whk.listener.eventlistener.listener.AbstractEventListener;

public class LogOutListener extends AbstractEventListener<LogoutEvent> {

    @Override
    public void dealEvent(LogoutEvent event) {
        System.out.println("登出事件：" + event.getUserId());
    }

}
