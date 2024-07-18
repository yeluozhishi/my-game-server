package com.whk.gamedb.mapper;

import com.whk.gamedb.entity.PlayerResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerResourceMapper extends JpaRepository<PlayerResourceEntity, Long> {
}
