package com.whk.listener.listener;

import com.whk.listener.EventEnum;
import com.whk.listener.EventUtil;

public class test {
    public static void main(String[] args) {
        LoginListener listener = new LoginListener();


        EventUtil.INSTANCE.addListener(EventEnum.LOGIN, new LoginListener());


    }
}
