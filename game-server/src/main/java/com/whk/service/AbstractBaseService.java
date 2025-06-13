package com.whk.service;

import com.whk.annotation.DBAroundAnnotation;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;


@Getter
public abstract class AbstractBaseService<T, ID> implements BaseService {


    private JpaRepository<T, ID> baseRepository;

    public abstract void setBaseRepository(JpaRepository<T, ID> baseRepository);

    protected void setRepository(JpaRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @DBAroundAnnotation()
    public List<T> findAll(long orderId) {
        return getBaseRepository().findAll();
    }

    @DBAroundAnnotation()
    public List<T> findAllByIds(long orderId, Iterable<ID> ids) {
        return getBaseRepository().findAllById(ids);
    }

    @DBAroundAnnotation()
    public Optional<T> find(ID id) {
        return getBaseRepository().findById(id);
    }

    @DBAroundAnnotation(hasReturn = false)
    public void deleteById(ID id) {
        getBaseRepository().deleteById(id);
    }

    @DBAroundAnnotation()
    public T create(long orderId, ID id, T entity) {
        return getBaseRepository().saveAndFlush(entity);
    }

    @DBAroundAnnotation(hasReturn = false)
    public void update(long orderId, T entity) {
        getBaseRepository().save(entity);
    }

    @DBAroundAnnotation()
    public boolean exists(ID id) {
        return getBaseRepository().existsById(id);
    }
}