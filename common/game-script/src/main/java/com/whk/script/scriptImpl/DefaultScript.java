package com.whk.script.scriptImpl;
import com.whk.script.annotation.Script;
import com.whk.script.scriptInterface.IDefaultScript;

@Script
public class DefaultScript implements IDefaultScript {
    @Override
    public void run() {
        System.out.println("do something...DefaultScript");
    }
}
