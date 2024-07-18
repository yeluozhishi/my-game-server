package com.whk.service.player;

import com.whk.gamedb.entity.PlayerBagEntity;
import com.whk.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerBagService extends AbstractBaseService<PlayerBagEntity, Long> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerBagEntity, Long> baseRepository) {
        setRepository(baseRepository);
    }
}
