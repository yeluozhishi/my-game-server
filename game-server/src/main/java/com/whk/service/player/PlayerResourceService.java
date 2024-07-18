package com.whk.service.player;

import com.whk.gamedb.entity.PlayerResourceEntity;
import com.whk.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerResourceService extends AbstractBaseService<PlayerResourceEntity, Long> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerResourceEntity, Long> baseRepository) {
        setRepository(baseRepository);
    }
}
