package com.whk.db.mapper;

import com.whk.db.entity.PlayerTemporaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerTemporaryMapper extends JpaRepository<PlayerTemporaryEntity, Long> {
}
