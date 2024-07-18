package com.whk.service.player;

import com.whk.gamedb.entity.PlayerEntity;
import com.whk.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * @author wanghongkun
 */
@Component
public class PlayerService extends AbstractBaseService<PlayerEntity, Long> {

    @Override
    @Autowired
    public void setBaseRepository(JpaRepository<PlayerEntity, Long> baseRepository) {
        setRepository(baseRepository);
    }

}
