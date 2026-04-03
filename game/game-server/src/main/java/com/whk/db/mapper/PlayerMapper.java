package com.whk.db.mapper;

import com.whk.db.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerMapper extends JpaRepository<PlayerEntity, Long> {
}
