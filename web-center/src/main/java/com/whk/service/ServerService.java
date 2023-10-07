package com.whk.service;


import com.whk.MessageI18n;
import com.whk.db.entity.ServerInfoEntity;
import com.whk.db.repository.ServerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.whk.message.MapBean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        server.setZone(zone);
        return serverDao.findAll(Example.of(server));
    }

    public Page<ServerInfoEntity> getServersByPage(int zone, int page, int size){
        if (zone == 0){
            Pageable pageable = PageRequest.of(page, page);
            return serverDao.findAll(pageable);
        }
        ServerInfoEntity server = new ServerInfoEntity();
        server.setZone(zone);
        return serverDao.findAll(Example.of(server), Pageable.ofSize(size));
    }

    public MapBean addServers(Long id, int zone, String instanceId, int serverType, String serverName, LocalDateTime openServerTime, LocalDateTime openEntranceTime){
        if (serverDao.existsById(id)){
            return MessageI18n.getMessage(7);
        }
        ServerInfoEntity server = new ServerInfoEntity();
        server.setId(id);
        server.setZone(zone);
        server.setInstanceId(instanceId);
        server.setServerType(serverType);
        server.setServerName(serverName);
        server.setOpenServerTime(Timestamp.valueOf(openServerTime));
        server.setOpenEntranceTime(Timestamp.valueOf(openEntranceTime));
        serverDao.save(server);
        return MessageI18n.getMessage(0);
    }

    public void delete(List<Long> ids){
        serverDao.deleteAllById(ids);
    }

}
