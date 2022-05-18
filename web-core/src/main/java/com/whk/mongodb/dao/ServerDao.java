package com.whk.mongodb.dao;

import com.whk.mongodb.Entity.Server;
import com.whk.mongodb.Repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class ServerDao extends AbstractDao<Server, Integer> {

    private ServerRepository serverRepository;

    @Autowired
    public void setServerRepositry(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    protected MongoRepository<Server, Integer> getMongoRepository() {
        return serverRepository;
    }

    @Override
    protected Class<Server> getEntityClass() {
        return Server.class;
    }


}
