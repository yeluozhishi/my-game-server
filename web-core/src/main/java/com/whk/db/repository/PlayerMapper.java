package com.whk.db.repository;

import com.whk.db.Entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/10
 */
public interface PlayerMapper extends CrudRepository<PlayerEntity, Long> {
}
