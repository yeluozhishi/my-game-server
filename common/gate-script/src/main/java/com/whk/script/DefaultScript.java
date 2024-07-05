package com.whk.script;

import script.annotation.Script;

@Script
public class DefaultScript implements IDefaultScript {
    @Override
    public void run() {
        System.out.println("do something...DefaultScript");
    }
}
