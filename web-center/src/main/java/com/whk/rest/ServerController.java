package com.whk.rest;

import com.whk.mongodb.Entity.Server;
import com.whk.network_param.ResponseEntity;
import com.whk.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("server")
public class ServerController {
    private static final Logger logger = Logger.getLogger(ServerController.class.getName());

    private ServerService service;

    @Autowired
    public void setService(ServerService service) {
        this.service = service;
    }

    @PostMapping(value = "list")
    public ResponseEntity<List<Server>> serverList(@RequestBody Map<String, String> map) {
        int zone = Integer.parseInt(map.getOrDefault("zone", "0"));
        return new ResponseEntity<>(service.getServers(zone));
    }

    @RequestMapping(value = "add")
    public ResponseEntity<String> addServer(HttpServletRequest request, @RequestBody Map<String, String> map) {
        int zone = Integer.parseInt(map.getOrDefault("zone", "0"));
        int port = Integer.parseInt(map.getOrDefault("port", "0"));
        int id = Integer.parseInt(map.getOrDefault("id", "0"));
        String ip = map.getOrDefault("ip", "");
        String serverName = map.getOrDefault("serverName", "");
        service.addServers(id, zone, port, ip, serverName);
        return new ResponseEntity<>("ok");
    }

}
