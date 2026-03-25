package com.whk;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseService<T, ID> {
    JpaRepository<T, ID> getBaseRepository();
}
