package com.whk.eventlistener;

import com.whk.eventlistener.listener.LogOutListener;
import com.whk.eventlistener.listener.LoginListener;
import com.whk.listener.eventlistener.EventEnum;
import com.whk.listener.eventlistener.EventUtil;
import com.whk.listener.eventlistener.IEvent;
import com.whk.listener.eventlistener.listener.AbstractEventListener;
import com.whk.threadpool.handler.IRecord;

public class GameEventRegister {
    public GameEventRegister() {
        EventUtil.INSTANCE.addListener(EventEnum.LOGIN, new LoginListener());
        EventUtil.INSTANCE.addListener(EventEnum.LOGOUT, new LogOutListener());
    }
}
