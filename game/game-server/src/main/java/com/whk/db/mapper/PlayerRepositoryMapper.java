package com.whk.db.mapper;

import com.whk.db.entity.PlayerRepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepositoryMapper extends JpaRepository<PlayerRepositoryEntity, Long> {
}
