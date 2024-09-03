package com.whk.eventlistener.listener;

import com.whk.eventlistener.event.LogoutEvent;
import com.whk.listener.eventlistener.listener.AbstractEventListener;
import com.whk.threadpool.ThreadType;

public class LogOutListener extends AbstractEventListener<LogoutEvent> {

    @Override
    public void dealEvent(LogoutEvent event) {
        System.out.println("登出事件：" + event.getUserId());
    }

    @Override
    protected boolean check(LogoutEvent event) {
        return true;
    }

    @Override
    public ThreadType threadType() {
        return ThreadType.PLAYER_THREAD;
    }
}
