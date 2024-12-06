package com.whk.eventlistener.listener;

import com.whk.eventlistener.event.LoginEvent;
import com.whk.listener.eventlistener.listener.AbstractEventListener;

public class LoginListener extends AbstractEventListener<LoginEvent> {

    @Override
    public void dealEvent(LoginEvent loginEvent) {
        System.out.println("登陆事件：" + loginEvent.getUserId());
    }

}
