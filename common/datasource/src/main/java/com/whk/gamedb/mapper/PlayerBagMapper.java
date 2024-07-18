package com.whk.gamedb.mapper;

import com.whk.gamedb.entity.PlayerBagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerBagMapper extends JpaRepository<PlayerBagEntity, Long> {
}
