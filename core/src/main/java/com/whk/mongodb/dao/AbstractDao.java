package com.whk.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public abstract class AbstractDao<Entity, ID> {
    protected abstract MongoRepository<Entity, ID> getMongoRepository();

    protected abstract Class<Entity> getEntityClass();

    public Optional<Entity> findById(ID id){
        Entity entity = null;

        Optional<Entity> op = this.getMongoRepository().findById(id);
        if (op.isPresent()){
            entity = op.get();
        }

        return Optional.ofNullable(entity);
    }

    public void saveOrUpdate(Entity entity){
        this.getMongoRepository().save(entity);
    }
}
