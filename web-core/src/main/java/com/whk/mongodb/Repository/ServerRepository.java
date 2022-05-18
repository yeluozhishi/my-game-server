package com.whk.mongodb.Repository;

import com.whk.mongodb.Entity.Server;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServerRepository extends MongoRepository<Server, Integer> {
}
