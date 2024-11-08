package com.whk.eventlistener;

import com.whk.eventlistener.listener.LogOutListener;
import com.whk.eventlistener.listener.LoginListener;
import com.whk.listener.eventlistener.EventEnum;
import com.whk.listener.eventlistener.EventUtil;
import com.whk.threadpool.handler.IRecord;

public class GameEventRegister {

    private static void addListener(EventEnum eventEnum, IRecord listener){
        EventUtil.INSTANCE.addListener(eventEnum, listener);
    }

    public static void registerListener(){
        addListener(EventEnum.LOGIN, new LoginListener());
        addListener(EventEnum.LOGOUT, new LogOutListener());
    }
}
