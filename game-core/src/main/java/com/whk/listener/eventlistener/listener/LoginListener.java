package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.event.LoginEvent;

public class LoginListener extends IEventListener<LoginEvent> {


    @Override
    public void dealEvent(LoginEvent loginEvent) {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean check(LoginEvent event) {
        return true;
    }

}
