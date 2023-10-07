package com.whk.db.repository;

import com.whk.db.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/10
 */
public interface PlayerMapper extends JpaRepository<PlayerEntity, Long> {
}
