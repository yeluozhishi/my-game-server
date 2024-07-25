package com.whk.listener.listener;

import com.whk.listener.event.LoginEvent;

public class LoginListener implements IEventListener<LoginEvent> {


    @Override
    public void dealEvent(LoginEvent loginEvent) {
        System.out.println("处理登录事件：" + loginEvent.getUserId());
    }

}
