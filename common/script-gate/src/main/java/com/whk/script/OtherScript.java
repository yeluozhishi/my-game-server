package com.whk.script;


import script.annotation.Script;

import java.time.LocalDateTime;

@Script
public class OtherScript implements IOtherScript {

    private final int a =1;

    public void run() {
        System.out.printf("%sdo something...OtherScript %n", LocalDateTime.now());
        p();
    }

    public void p() {

    }
}
