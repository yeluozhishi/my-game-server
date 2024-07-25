package com.whk.service;


import com.whk.MessageI18n;
import com.whk.centerdb.entity.ServerInfoEntity;
import com.whk.centerdb.repository.ServerInfoMapper;
import com.whk.message.MapBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class ServerService {

    private ServerInfoMapper serverDao;

    @Autowired
    public void setServerDao(ServerInfoMapper serverDao) {
        this.serverDao = serverDao;
    }

    public List<ServerInfoEntity> getServers(int zone){
        if (zone == 0){
            return serverDao.findAll();
        }
        ServerInfoEntity server = new ServerInfoEntity();
        server.setServerZone(zone);
        return serverDao.findAll(Example.of(server));
    }

    public Page<ServerInfoEntity> getServersByPage(int zone, int page, int size){
        if (zone == 0){
            Pageable pageable = PageRequest.of(page, page);
            return serverDao.findAll(pageable);
        }
        ServerInfoEntity server = new ServerInfoEntity();
        server.setServerZone(zone);
        return serverDao.findAll(Example.of(server), Pageable.ofSize(size));
    }

    public MapBean addServers(Integer id, int zone, int serverType, String serverName, LocalDateTime openServerTime, LocalDateTime openEntranceTime){
        if (serverDao.existsById(id)){
            return MessageI18n.getMessage(7);
        }
        ServerInfoEntity server = new ServerInfoEntity();
        server.setId(id);
        server.setServerZone(zone);
        server.setServerType(serverType);
        server.setServerName(serverName);
        server.setOpenServerTime(openServerTime.toInstant(ZoneOffset.UTC));
        server.setOpenEntranceTime(openEntranceTime.toInstant(ZoneOffset.UTC));
        serverDao.save(server);
        return MessageI18n.getMessage(0);
    }

    public void delete(List<Integer> ids){
        serverDao.deleteAllById(ids);
    }
}
