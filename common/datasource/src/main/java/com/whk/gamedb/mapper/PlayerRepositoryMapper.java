package com.whk.gamedb.mapper;

import com.whk.gamedb.entity.PlayerRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepositoryMapper extends JpaRepository<PlayerRepositoryEntity, Long> {
}
