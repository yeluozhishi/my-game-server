package org.whk.game.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.whk.game.entity.PlayerEntity;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/10
 */
@Repository
public interface PlayerMapper extends JpaRepository<PlayerEntity, Long> {
}
