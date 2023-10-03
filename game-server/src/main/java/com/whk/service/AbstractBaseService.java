package com.whk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class AbstractBaseService<T, ID>{

    private JpaRepository<T, ID> baseRepository;

    @Autowired
    public void setBaseRepository(JpaRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    public JpaRepository<T, ID> getBaseRepository() {
        return baseRepository;
    }

    public List<T> findAll() {
        return this.baseRepository.findAll();
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