package com.whk.service;

import com.whk.mongodb.Entity.Server;
import com.whk.mongodb.dao.ServerDao;
import com.whk.network_param.MapBean;
import com.whk.util.MessageI18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServerService {

    private ServerDao serverDao;

    @Autowired
    public void setServerDao(ServerDao serverDao) {
        this.serverDao = serverDao;
    }

    public List<Server> getServers(int zone){
        if (zone == 0){
            return serverDao.getMongoRepository().findAll();
        }
        Server server = new Server();
        server.setZone(zone);
        return serverDao.findAllByEntity(server);
    }

    public Page<Server> getServersByPage(int zone, int page, int size){
        if (zone == 0){
            Pageable pageable = PageRequest.of(page, page);
            return serverDao.getMongoRepository().findAll(pageable);
        }
        Server server = new Server();
        server.setZone(zone);
        return serverDao.getMongoRepository().findAll(Example.of(server), Pageable.ofSize(size));
    }

    public MapBean addServers(int id, int zone, String instanceId, int serverType, String serverName, LocalDateTime openServerTime, LocalDateTime openEntranceTime){
        if (serverDao.getMongoRepository().existsById(id)){
            return MessageI18n.getMessage(7);
        }
        Server server = new Server(id, zone, instanceId, serverType, serverName, openServerTime, openEntranceTime);
        serverDao.insert(server, server.getId());
        return MessageI18n.getMessage(0);
    }

    public void insertAndUpdate(int id, int zone, String instanceId, int serverType, String serverName, LocalDateTime openServerTime, LocalDateTime openEntranceTime){
        Server server = new Server(id, zone, instanceId, serverType, serverName, openServerTime, openEntranceTime);
        serverDao.saveOrUpdate(server);
    }

    public void delete(ArrayList<Integer> ids){
        serverDao.getMongoRepository().deleteAllById(ids);
    }

}
