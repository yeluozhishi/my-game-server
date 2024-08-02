package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.event.LoginEvent;

public class LoginListener extends IEventListener<LoginEvent> {


    @Override
    public void dealEvent(LoginEvent loginEvent) {
        System.out.println("登陆事件：" + loginEvent.getUserId());
    }

    @Override
    protected boolean check(LoginEvent event) {
        return true;
    }

}
