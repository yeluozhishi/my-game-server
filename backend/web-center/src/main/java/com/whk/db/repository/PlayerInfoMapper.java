package com.whk.db.repository;


import com.whk.db.entity.PlayerInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface PlayerInfoMapper extends JpaRepository<PlayerInfoEntity, Long> {
}
