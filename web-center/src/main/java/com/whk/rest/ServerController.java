package com.whk.rest;

import cn.hutool.core.bean.BeanUtil;
import com.whk.MessageI18n;
import com.whk.db.entity.ServerInfoEntity;
import com.whk.service.ServerService;
import org.whk.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.whk.message.MapBean;
import org.whk.message.ReqServerListMessage;
import org.whk.message.Server;

import java.time.LocalDateTime;
import java.util.List;
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
    public List<Server> serverList(@RequestBody ReqServerListMessage message) {
        int zone = message.getZone();
        var list = service.getServers(zone);
        return BeanUtil.copyToList(list, Server.class);
    }

    @RequestMapping(value = "add")
    public MapBean addServer(HttpServletRequest request, @RequestBody MapBean map) {
        int zone = map.getInt("zone", 0);
        var id = map.getLong("id", 0L);
        int serverType = map.getInt("serverType", 0);
        String instanceId = map.getString("instanceId", "");
        String serverName = map.getString("serverName", "");
        LocalDateTime openServerTime = map.getLocalDateTime("openServerTime", DateUtils.getFormatter());
        LocalDateTime openEntranceTime = map.getLocalDateTime("openEntranceTime", DateUtils.getFormatter());
        return service.addServers(id, zone, instanceId, serverType, serverName, openServerTime, openEntranceTime);
    }

    @RequestMapping(value = "update")
    public MapBean updateServer(HttpServletRequest request, @RequestBody MapBean map) {
        int zone = map.getInt("zone", 0);
        Long id = map.getLong("id", 0L);
        int serverType = map.getInt("serverType", 0);
        String instanceId = map.getString("instanceId", "");
        String serverName = map.getString("serverName", "");
        LocalDateTime openServerTime = map.getLocalDateTime("openServerTime", DateUtils.getFormatterT());
        LocalDateTime openEntranceTime = map.getLocalDateTime("openEntranceTime", DateUtils.getFormatterT());
        service.addServers(id, zone, instanceId, serverType, serverName, openServerTime, openEntranceTime);
        return MessageI18n.getMessage(0);
    }

    @RequestMapping(value = "delete")
    public MapBean deleteServer(HttpServletRequest request, @RequestBody MapBean map) {
        service.delete(map.getListForLong("serverIds"));
        return MessageI18n.getMessage(0);
    }

}
