package com.whk.service;

import com.whk.aop.AroundAnnotation;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


@Getter
public abstract class AbstractBaseService<T, ID> implements BaseService{

    private JpaRepository<T, ID> baseRepository;

    public abstract void setBaseRepository(JpaRepository<T, ID> baseRepository);

    protected void setRepository(JpaRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @AroundAnnotation()
    public List<T> findAll(long orderId) {
        return this.baseRepository.findAll();
    }

    @AroundAnnotation()
    public List<T> findAllByIds(long orderId, Iterable<ID> ids) {
        return this.baseRepository.findAllById(ids);
    }

    @AroundAnnotation()
    public Optional<T> find(ID id) {
        return this.baseRepository.findById(id);
    }

    @AroundAnnotation(hasReturn = false)
    public void deleteById(ID id) {
        this.baseRepository.deleteById(id);
    }

    @AroundAnnotation(hasReturn = false)
    public void delete(long orderId, T entity) {
        this.baseRepository.delete(entity);
    }

    @AroundAnnotation()
    public <S extends T> S create(long orderId, S entity) {
        return this.baseRepository.saveAndFlush(entity);
    }

    @AroundAnnotation(hasReturn = false)
    public <S extends T> void update(long orderId, S entity) {
        this.baseRepository.save(entity);
    }

    @AroundAnnotation()
    protected Page<T> page(long orderId, Pageable pageable) {
        return null;
    }

    @AroundAnnotation()
    public boolean exists(ID id) {
        return this.baseRepository.existsById(id);
    }
}