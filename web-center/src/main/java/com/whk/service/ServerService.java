package com.whk.service;

import com.whk.mongodb.Entity.Server;
import com.whk.mongodb.dao.ServerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    private ServerDao serverDao;

    @Autowired
    public void setServerDao(ServerDao serverDao) {
        this.serverDao = serverDao;
    }

    public List<Server> getServers(int zone){
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

    public void addServers(int id, int zone, int port, String ip, String serverName){
        Server server = new Server(id, serverName, ip, port, zone);
        serverDao.saveOrUpdate(server);
    }


}
