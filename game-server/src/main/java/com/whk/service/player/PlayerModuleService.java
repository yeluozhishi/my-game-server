package com.whk.service.player;


import com.whk.actor.component.PlayerModule;
import com.whk.gamedb.entity.PlayerModuleEntity;
import com.whk.service.AbstractComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerModuleService extends AbstractComponentService<PlayerModuleEntity, PlayerModule> {
    @Autowired
    @Override
    public void setBaseRepository(JpaRepository<PlayerModuleEntity, Long> baseRepository) {
        setRepository(baseRepository);
    }

    @Override
    public byte[] transferToObject(PlayerModuleEntity playerModuleEntity) {
        return playerModuleEntity.getData();
    }

    @Override
    public Class<PlayerModule> getComponentClass() {
        return PlayerModule.class;
    }
}
