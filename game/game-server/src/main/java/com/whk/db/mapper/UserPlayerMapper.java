package com.whk.db.mapper;

import com.whk.db.entity.UserPlayerEntity;
import com.whk.db.entity.UserPlayerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPlayerMapper extends JpaRepository<UserPlayerEntity, UserPlayerPK> {
}
