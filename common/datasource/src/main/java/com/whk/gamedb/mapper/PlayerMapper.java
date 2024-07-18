package com.whk.gamedb.mapper;

import com.whk.gamedb.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerMapper extends JpaRepository<PlayerEntity, Long> {
}
