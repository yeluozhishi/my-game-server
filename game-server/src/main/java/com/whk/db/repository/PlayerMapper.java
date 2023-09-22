package com.whk.db.repository;

import com.whk.db.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/10
 */
@Repository
public interface PlayerMapper extends CrudRepository<PlayerEntity, Long> {
}
