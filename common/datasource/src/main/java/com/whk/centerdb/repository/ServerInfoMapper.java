package com.whk.centerdb.repository;

import com.whk.centerdb.entity.ServerInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 */
@Repository
@Transactional
public interface ServerInfoMapper extends JpaRepository<ServerInfoEntity, Integer> {

}
