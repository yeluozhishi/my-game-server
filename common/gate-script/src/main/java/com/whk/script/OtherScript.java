package com.whk.script;


import script.annotation.Script;

import java.time.LocalDateTime;

@Script
public class OtherScript implements IOtherScript {
    @Override
    public void run() {
        System.out.printf("%sdo something...OtherScript %n", LocalDateTime.now());
    }
}
