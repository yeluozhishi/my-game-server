package com.whk.service;

import com.whk.mongodb.Entity.Server;
import com.whk.mongodb.dao.ServerDao;
import com.whk.network_param.MapBean;
import com.whk.network_param.WebCenterError;
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
        return serverDao.getMongoRepository().findAll(Example.of(server), Pageable.ofSize(20));
    }

    public MapBean addServers(int id, int zone, String serverName, LocalDateTime openServerTime, LocalDateTime openEntranceTime){
        if (serverDao.getMongoRepository().existsById(id)){
            return new MapBean(WebCenterError.INCREASE_REPEAT);
        }
        Server server = new Server(id, serverName, zone, openServerTime, openEntranceTime);
        serverDao.insert(server, server.getId());
        return MapBean.success();
    }

    public void insertAndUpdate(int id, int zone, String serverName, LocalDateTime openServerTime, LocalDateTime openEntranceTime){
        Server server = new Server(id, serverName, zone, openServerTime, openEntranceTime);
        serverDao.saveOrUpdate(server);
    }

    public void delete(ArrayList<Integer> ids){
        serverDao.getMongoRepository().deleteAllById(ids);
    }

}
