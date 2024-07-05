package com.whk.centerdb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.whk.centerdb.entity.PlayerInfoEntity;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/10
 */
@Repository
@Transactional
public interface PlayerInfoMapper extends JpaRepository<PlayerInfoEntity, Long> {
}
