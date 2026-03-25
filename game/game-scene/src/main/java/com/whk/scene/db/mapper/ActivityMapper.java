package com.whk.scene.db.mapper;

import com.whk.scene.db.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityMapper extends JpaRepository<ActivityEntity, Integer> {
}
