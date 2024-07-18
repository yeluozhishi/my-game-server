package com.whk.service.player;

import com.whk.gamedb.entity.PlayerRepositoryEntity;
import com.whk.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerRepositoryService extends AbstractBaseService<PlayerRepositoryEntity, Long> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerRepositoryEntity, Long> baseRepository) {
        setRepository(baseRepository);
    }
}
