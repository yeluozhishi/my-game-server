package com.whk.mongodb.dao;

import com.whk.mongodb.Entity.Server;
import com.whk.mongodb.Repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerDao {

    private ServerRepository serverRepository;

    @Autowired
    public void setServerRepositry(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }




}
