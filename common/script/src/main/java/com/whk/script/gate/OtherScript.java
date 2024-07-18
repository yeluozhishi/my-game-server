package com.whk.script.gate;


import script.annotation.Script;

import java.time.LocalDateTime;

@Script
public class OtherScript implements IOtherScript {

    private int a =1;

    public void run() {
        System.out.printf("%sdo something...OtherScript %n", LocalDateTime.now());
        p();
    }

    public void p() {

    }
}
