package com.whk.script;


import script.annotation.Script;


@Script
public class DefaultScript1 implements IDefaultScript {
    @Override
    public void run() {
        System.out.println("do something...DefaultScript1");
    }
}
