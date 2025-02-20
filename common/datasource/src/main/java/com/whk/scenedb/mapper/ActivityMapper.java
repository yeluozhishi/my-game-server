package com.whk.scenedb.mapper;

import com.whk.scenedb.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityMapper extends JpaRepository<ActivityEntity, Integer> {
}
