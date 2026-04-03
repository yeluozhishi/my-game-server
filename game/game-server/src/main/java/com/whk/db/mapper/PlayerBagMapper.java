package com.whk.db.mapper;

import com.whk.db.entity.PlayerBagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerBagMapper extends JpaRepository<PlayerBagEntity, Long> {
}
