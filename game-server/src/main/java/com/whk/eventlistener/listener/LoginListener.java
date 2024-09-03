package com.whk.eventlistener.listener;

import com.whk.eventlistener.event.LoginEvent;
import com.whk.listener.eventlistener.listener.AbstractEventListener;
import com.whk.threadpool.ThreadType;

public class LoginListener extends AbstractEventListener<LoginEvent> {

    @Override
    public void dealEvent(LoginEvent loginEvent) {
        System.out.println("登陆事件：" + loginEvent.getUserId());
    }

    @Override
    protected boolean check(LoginEvent event) {
        return true;
    }

    @Override
    public ThreadType threadType() {
        return ThreadType.PLAYER_THREAD;
    }
}
