package com.whk.script.scriptImpl;


import com.whk.script.annotation.Script;
import com.whk.script.scriptInterface.IOtherScript;

import java.time.LocalDateTime;

@Script
public class OtherScript implements IOtherScript {
    @Override
    public void run() {
        System.out.printf("%sdo something...OtherScript   111111%n", LocalDateTime.now());
    }
}
