package com.whk.service;

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

    public void setRepository(JpaRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    public List<T> findAll() {
        return this.baseRepository.findAll();
    }

    public List<T> findAllByIds(Iterable<ID> ids) {
        return this.baseRepository.findAllById(ids);
    }

    public Optional<T> find(ID id) {
        return this.baseRepository.findById(id);
    }

    public void deleteById(ID id) {
        this.baseRepository.deleteById(id);
    }

    public void delete(T entity) {
        this.baseRepository.delete(entity);
    }

    public <S extends T> S create(S entity) {
        return this.baseRepository.saveAndFlush(entity);
    }

    public <S extends T> S update(S entity) {
        return this.baseRepository.saveAndFlush(entity);
    }

    protected Page<T> page(Pageable pageable) {
        return null;
    }

    public boolean exists(ID id) {
        return this.baseRepository.existsById(id);
    }
}