package com.whk.eventlistener.listener;

import com.whk.eventlistener.event.LoginEvent;
import com.whk.listener.eventlistener.listener.AbstractEventListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginListener extends AbstractEventListener<LoginEvent> {

    @Override
    public void dealEvent(LoginEvent loginEvent) {
        log.info("登陆事件：" + loginEvent.getUserId());
    }

}
