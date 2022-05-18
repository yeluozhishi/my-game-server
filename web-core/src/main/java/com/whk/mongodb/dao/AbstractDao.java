package com.whk.mongodb.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
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

    public Optional<Entity> findByEntity(Entity entity){
        return this.getMongoRepository().findOne(Example.of(entity));
    }

    public Optional<Entity> findByEntity(Entity entity, ExampleMatcher matcher){
        return this.getMongoRepository().findOne(Example.of(entity, matcher));
    }

    public List<Entity> findAllByEntity(Entity entity){
        return this.getMongoRepository().findAll(Example.of(entity));
    }

    public List<Entity> findAllByEntity(Entity entity, ExampleMatcher matcher){
        return this.getMongoRepository().findAll(Example.of(entity, matcher));
    }

    public List<Entity> saveAllByEntity(List<Entity> entities){
        return this.getMongoRepository().saveAll(entities);
    }

    public void deleteByEntity(List<Entity> entities){
        this.getMongoRepository().deleteAll(entities);
    }

    public void deleteByEntity(Entity entities){
        this.getMongoRepository().delete(entities);
    }

    public void saveOrUpdate(Entity entity){
        this.getMongoRepository().save(entity);
    }
}
