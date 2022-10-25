package com.whk.rest;

import com.whk.network_param.MapBean;
import com.whk.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public MapBean serverList(@RequestBody Map<String, String> map) {
        int zone = Integer.parseInt(map.getOrDefault("zone", "0"));
        return new MapBean(Map.of("serverList", service.getServers(zone)));
    }

    @RequestMapping(value = "add")
    public MapBean addServer(HttpServletRequest request, @RequestBody Map<String, String> map) {
        int zone = Integer.parseInt(map.getOrDefault("zone", "0"));
        int port = Integer.parseInt(map.getOrDefault("port", "0"));
        int id = Integer.parseInt(map.getOrDefault("id", "0"));
        String ip = map.getOrDefault("ip", "");
        String serverName = map.getOrDefault("serverName", "");
        service.addServers(id, zone, port, ip, serverName);
        return new MapBean();
    }

}
