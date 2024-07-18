package com.whk.gamedb.mapper;

import com.whk.gamedb.entity.PlayerModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerModuleMapper extends JpaRepository<PlayerModuleEntity, Long> {
}
