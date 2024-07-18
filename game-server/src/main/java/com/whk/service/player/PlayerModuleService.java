package com.whk.service.player;


import com.whk.gamedb.entity.PlayerModuleEntity;
import com.whk.service.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerModuleService extends AbstractBaseService<PlayerModuleEntity, Long> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerModuleEntity, Long> baseRepository) {
        setRepository(baseRepository);
    }
}
