package com.whk.script.other;


import com.whk.script.IOtherScript;
import script.annotation.Script;

import java.time.LocalDateTime;

@Script
public class OtherScript implements IOtherScript {


    @Override
    public void call() {
        System.out.printf("%sdo something...OtherScript %n", LocalDateTime.now());
        System.out.println("firstCall");
    }

    public void firstCall() {
        System.out.println("firstCall11111");
        System.out.printf("%sdo something...OtherScript %n", LocalDateTime.now());
        System.out.println("firstCall11111");
    }
}
