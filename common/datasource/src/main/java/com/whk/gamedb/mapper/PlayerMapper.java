package com.whk.gamedb.mapper;

import com.whk.gamedb.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/10
 */
@Repository
public interface PlayerMapper extends JpaRepository<PlayerEntity, Long> {
}
