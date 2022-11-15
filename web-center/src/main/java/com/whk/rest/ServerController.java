package com.whk.rest;

import com.whk.network_param.MapBean;
import com.whk.service.ServerService;
import com.whk.util.MessageI18n;
import com.whk.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
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
    public MapBean serverList(@RequestBody MapBean map) {
        int zone = map.getInt("zone", 0);
        return new MapBean(Map.of("serverList", service.getServers(zone)));
    }

    @RequestMapping(value = "add")
    public MapBean addServer(HttpServletRequest request, @RequestBody MapBean map) {
        int zone = map.getInt("zone", 0);
        int id = map.getInt("id", 0);
        int serverType = map.getInt("serverType", 0);

        String serverName = map.getString("serverName", "");

        LocalDateTime openServerTime = map.getLocalDateTime("openServerTime", Util.getFormatter());
        LocalDateTime openEntranceTime = map.getLocalDateTime("openEntranceTime", Util.getFormatter());
        return service.addServers(id, zone, serverType, serverName, openServerTime, openEntranceTime);
    }

    @RequestMapping(value = "update")
    public MapBean updateServer(HttpServletRequest request, @RequestBody MapBean map) {
        int zone = map.getInt("zone", 0);
        int id = map.getInt("id", 0);
        int serverType = map.getInt("serverType", 0);

        String serverName = map.getString("serverName", "");

        LocalDateTime openServerTime = map.getLocalDateTime("openServerTime", Util.getFormatter());
        LocalDateTime openEntranceTime = map.getLocalDateTime("openEntranceTime", Util.getFormatter());
        service.insertAndUpdate(id, zone, serverType, serverName, openServerTime, openEntranceTime);
        return MessageI18n.getMessage(0);
    }

    @RequestMapping(value = "delete")
    public MapBean deleteServer(HttpServletRequest request, @RequestBody MapBean map) {
        var ids = (ArrayList<Integer>) map.get("serverIds");
        service.delete(ids);
        return MessageI18n.getMessage(0);
    }

}
