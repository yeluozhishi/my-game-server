package com.whk.controller;

import com.whk.server.GateServerManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gate-server")
public class GateServerController {

    @GetMapping("updateServers")
    public void updateServers(){
        GateServerManager.getInstance().getConfigServers();
    }

}
